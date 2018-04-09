package com.example.android.whatshot.utilities;

import android.net.Uri;
import android.util.Log;

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
    public static URL buildUrl(String popularTimesSearchQuery) {
        Uri builtUri = Uri.parse(POPULARTIME_BASE_URL).buildUpon()
                .build();

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
