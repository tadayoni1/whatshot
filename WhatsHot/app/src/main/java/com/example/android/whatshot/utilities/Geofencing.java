package com.example.android.whatshot.utilities;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.whatshot.MainActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by hammedopejin on 4/1/18.
 */

public class Geofencing implements ResultCallback<Status> {
    // Constants
    public static final String TAG = Geofencing.class.getSimpleName();
    private static final float GEOFENCE_RADIUS = 500; // 500 meters
    private static final long GEOFENCE_TIMEOUT = 24 * 60 * 60 * 1000; // 24 hours

    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private Geofence mGeofence;
    private PendingIntent mGeofencePendingIntent;
    private Location mLocation;

    public Geofencing(Context context, GoogleApiClient client, Location location) {
        mContext = context;
        mGoogleApiClient = client;
        mGeofencePendingIntent = null;
        mLocation = location;
    }


    public void updateGeofence(){

        if (mLocation == null) return;

            mGeofence = new Geofence.Builder()
                    .setExpirationDuration(GEOFENCE_TIMEOUT)
                    .setCircularRegion(mLocation.getLatitude(), mLocation.getLongitude(), GEOFENCE_RADIUS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .setRequestId(TAG)
                    .build();
    }

    private GeofencingRequest getGeofencingRequest(){
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofence(mGeofence);
        return builder.build();
    }


    private PendingIntent getGeofencePendingIntent(){
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }

        Intent intent = new Intent(mContext, MainActivity.GeofenceBroadcastReceiver.class);
        mGeofencePendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);

        return mGeofencePendingIntent;
    }

    public void registerGeofence(){
        if(!mGoogleApiClient.isConnected() || mGoogleApiClient == null
                || mGeofence == null){
            return;
        }

        try {
            LocationServices.GeofencingApi.addGeofences(mGoogleApiClient,
                    getGeofencingRequest(),
                    getGeofencePendingIntent()
            ).setResultCallback(this);
            Log.e(TAG, "Suceessfully registered geofence : %s");
        }catch (SecurityException securityException){
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            Log.e(TAG, securityException.getMessage());
        }
    }

    public void unRegisterGeofence(){
        if(mGoogleApiClient == null || !mGoogleApiClient.isConnected()){
            return;
        }

        try {
            LocationServices.GeofencingApi.removeGeofences(mGoogleApiClient,
                    getGeofencePendingIntent()
            ).setResultCallback(this);
            Log.e(TAG, "Suceessfully unregistered geofence : %s");
        }catch (SecurityException securityException){
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            Log.e(TAG, securityException.getMessage());
        }
    }

    @Override
    public void onResult(@NonNull Status result) {
        Log.e(TAG, String.format("Error adding/removing geofence : %s",
                result.getStatus().toString()));
    }
}
