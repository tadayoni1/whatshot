package com.example.android.whatshot.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.android.whatshot.data.PlaceTypes;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Pedram on 3/18/2018.
 */

// TODO: Soheil, Pedram
// Put helper methods for connecting to network that is required by the content provider in here

public final class NetworkUtils {


    // TODO: Implement loading current data from API (SearchData?)


    private final static String POPULARTIME_BASE_URL = "http://192.241.231.216/populartimes-api-test/api.php";

    private static final String LAT1 = "lat1";
    private static final String LAT2 = "lat2";
    private static final String LNG1 = "lng1";
    private static final String LNG2 = "lng2";
    private static final String PLACE_TYPE = "type";


    public static URL buildUrl(String popularTimesSearchQuery, LatLng latLng1, LatLng latLng2, PlaceTypes.PlacesTypes placeType) {
        Uri builtUri = Uri.parse(POPULARTIME_BASE_URL).buildUpon()
                .appendQueryParameter(LAT1, Double.toString(latLng1.latitude))
                .appendQueryParameter(LNG1, Double.toString(latLng1.longitude))
                .appendQueryParameter(LAT2, Double.toString(latLng2.latitude))
                .appendQueryParameter(LNG2, Double.toString(latLng2.longitude))
                .appendQueryParameter(PLACE_TYPE, placeType.toString())
                .build();

        Log.d("NetworkUtils-buildUrl", builtUri.toString() );

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Log.d("getResponseFromHttpUrl", "started");
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            Log.d("getResponseFromHttpUrl", "scanner initialized");

            boolean hasInput = scanner.hasNext();
            Log.d("getResponseFromHttpUrl", hasInput + "");
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
