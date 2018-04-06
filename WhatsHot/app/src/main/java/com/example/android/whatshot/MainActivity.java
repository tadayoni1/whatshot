package com.example.android.whatshot;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.whatshot.data.PopularTimesContract;
import com.example.android.whatshot.utilities.FakeDataUtils;
import com.example.android.whatshot.utilities.Geofencing;
import com.example.android.whatshot.utilities.NetworkUtils;
import com.example.android.whatshot.utilities.WhatsHotJsonUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

// TODO: Kenda, Hamed

public class MainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private PopularTimesAdapter mPopularTimesAdapter;
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private ProgressBar mLoadingIndicator;

    public static final int POPULARTIMES_SEARCH_LOADER_ID = 22;
    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 111;

    private static final String SEARCH_QUERY_URL_EXTRA = "query";
    private static final String SEARCH_QUERY_URL_EXTRA_DAY = "day";
    private static final String SEARCH_QUERY_URL_EXTRA_HOUR = "hour";

    private GoogleApiClient mClient;
    private Geofencing mGeofencing;
    private Location location;

    private Boolean leftGeofence;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mPopularTimesAdapter = new PopularTimesAdapter(this);

        mRecyclerView.setAdapter(mPopularTimesAdapter);

        showLoading();

        /*
         * Initialize the loader
         */
        makePopularTimesSearchQuery(0, 12);

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Your location is required for app to function", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_FINE_LOCATION);
        } else {
            mClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .enableAutoManage(this, this)
                    .build();
            location = LocationServices.FusedLocationApi.getLastLocation(mClient);
        }

        mGeofencing = new Geofencing(this, mClient, location);
        mGeofencing.updateGeofence();
        mGeofencing.registerGeofence();
        leftGeofence = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_FINE_LOCATION && (ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            location = LocationServices.FusedLocationApi.getLastLocation(mClient);
        } else {
            Toast.makeText(getApplicationContext(), "Your location is required for app to function", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, final Bundle args) {

        switch (loaderId) {
            case POPULARTIMES_SEARCH_LOADER_ID:
                // TODO: Implement to load data from API when data is not current
                boolean isDataCurrent = false;
                if (!isDataCurrent) {
                    readJsonFromApi(args);
                }

                Uri populartimesQueryUri = PopularTimesContract.VenueEntry.buildVenueUriWithDayAndHour(args.getInt(SEARCH_QUERY_URL_EXTRA_DAY), args.getInt(SEARCH_QUERY_URL_EXTRA_HOUR));

                Log.d(getClass().toString(), "populartimesQueryUri: " + populartimesQueryUri);
                return new CursorLoader(this,
                        populartimesQueryUri,
                        null,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);

        }

    }


    // This method must only be called within a Loader
    public boolean readJsonFromApi(final Bundle args) {
        ContentResolver contentResolver = getContentResolver();

        contentResolver.delete(PopularTimesContract.VenueEntry.CONTENT_URI,
                null,
                null);

        contentResolver.delete(PopularTimesContract.VenueEntry.VenueHoursEntry.CONTENT_URI,
                null,
                null);

        /* Extract the search query from the args using our constant */
        String searchQueryUrlString = args.getString(SEARCH_QUERY_URL_EXTRA);

        Log.d(getClass().toString(), "searchQueryUrlString in Loader: " + searchQueryUrlString.toString());
        /* If the user didn't enter anything, there's nothing to search for */
        if (searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString)) {
            return false;
        }

        /* Parse the URL from the passed in String and perform the search */
        try {
            URL populartimesUrl = new URL(searchQueryUrlString);
            Log.d(getClass().toString(), "populartimesUrl in Loader: " + populartimesUrl);
//                    String populartimesSearchResults = NetworkUtils.getResponseFromHttpUrl(populartimesUrl);
            String populartimesSearchResults = FakeDataUtils.samplePopulartimesJson;
            Log.d(getClass().toString(), "populartimesSearchResults in Loader: " + populartimesSearchResults);
//                    return WhatsHotJsonUtils.sortByDayAndHour(populartimesSearchResults, 0, 0);
            ContentValues[] contentValues = WhatsHotJsonUtils.getVenueContentValuesFromJsonString(populartimesSearchResults, this);
            Log.d(getClass().toString(), "First row of Venues Content Values in loader: " + contentValues[0].toString());
            contentResolver.bulkInsert(PopularTimesContract.VenueEntry.CONTENT_URI, contentValues);
            contentValues = WhatsHotJsonUtils.getHoursContentValuesFromJsonString(populartimesSearchResults);
            Log.d(getClass().toString(), "First row of Hours Content Values in loader: " + contentValues[0].toString());
            contentResolver.bulkInsert(PopularTimesContract.VenueEntry.VenueHoursEntry.CONTENT_URI, contentValues);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (null != data) {
            mPopularTimesAdapter.swapCursor(data);
            Log.d(getClass().toString(), "onLoadFinished data size: " + data.getCount());
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            mRecyclerView.smoothScrollToPosition(mPosition);
            if (data.getCount() != 0) showDataView();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPopularTimesAdapter.swapCursor(null);
    }

    private void showDataView() {
        /* First, hide the loading indicator */
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        /* Finally, make sure the data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        /* Then, hide the data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Finally, show the loading indicator */
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    /**
     * This is where we inflate and set up the menu for this Activity.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */

    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void makePopularTimesSearchQuery(int day, int hour) {
        URL populartimesSearchUrl = NetworkUtils.buildUrl("");

        Log.d(getClass().toString(), "makePopularTimesSearchQuery() URL: " + populartimesSearchUrl.toString());
        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, populartimesSearchUrl.toString());
        queryBundle.putInt(SEARCH_QUERY_URL_EXTRA_DAY, day);
        queryBundle.putInt(SEARCH_QUERY_URL_EXTRA_HOUR, hour);
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> populartimesSearchLoader = loaderManager.getLoader(POPULARTIMES_SEARCH_LOADER_ID);
        if (populartimesSearchLoader == null) {
            loaderManager.initLoader(POPULARTIMES_SEARCH_LOADER_ID, queryBundle, this);
        } else {
            loaderManager.restartLoader(POPULARTIMES_SEARCH_LOADER_ID, queryBundle, this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mClient.isConnecting() || !mClient.isConnected()) {
            mClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mClient.isConnecting() || mClient.isConnected()) {
            mClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (leftGeofence) {
            //TODO: Refresh API call
            mGeofencing.updateGeofence();
            mGeofencing.registerGeofence();
        }
        Log.i(TAG, "API Client Connection Successful!");
    }

    @Override
    public void onConnectionSuspended(int i) {
        mClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "API Client Connection Failed!");
    }

    public class GeofenceBroadcastReceiver extends BroadcastReceiver {

        public final String TAG = GeofenceBroadcastReceiver.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i(TAG, "onReceive called");


            GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

            if (geofencingEvent.hasError()) {
                Log.e(TAG, String.format("Error code : %d", geofencingEvent.getErrorCode()));
                return;
            }

            // Get the transition type.
            int geofenceTransition = geofencingEvent.getGeofenceTransition();
            // Check which transition type has triggered this event
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                //TODO what to do when reentring geofence?
            } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
                leftGeofence = true;
                mGeofencing.unRegisterGeofence();
            } else {
                // Log the error.
                Log.e(TAG, String.format("Unknown transition : %d", geofenceTransition));
                // No need to do anything else
                return;
            }


        }
    }
}
