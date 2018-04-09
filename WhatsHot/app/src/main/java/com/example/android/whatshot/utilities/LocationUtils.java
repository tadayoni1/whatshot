package com.example.android.whatshot.utilities;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class LocationUtils {

    // radius in km
    public static LatLng[] getRectangleBoundary(LatLng location, double radius){

        LatLng[] results = new LatLng[2];

        double R = 6371;  // earth radius in km

        double lat = location.latitude;
        double lon = location.longitude;

        double x1 = lon - Math.toDegrees(radius/R/Math.cos(Math.toRadians(lat)));
        double y1 = lat + Math.toDegrees(radius/R);

        results[0] = new LatLng(y1,x1);


        double x2 = lon + Math.toDegrees(radius/R/Math.cos(Math.toRadians(lat)));
        double y2 = lat - Math.toDegrees(radius/R);

        results[1] = new LatLng(y2,x2);

        return results;
    }



}
