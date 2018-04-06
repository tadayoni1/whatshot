package com.example.android.whatshot.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.android.whatshot.R;
import com.example.android.whatshot.data.PopularTimesContract;
import com.example.android.whatshot.data.WhatsHotJsonContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Pedram on 3/18/2018.
 */

// TODO: Soheil, Pedram
// Put helper methods for parsing and processing Json that is required by the content provider in here


public final class WhatsHotJsonUtils {


    /*
     * day: MONDAY = 0, TUESDAY = 1, WEDNESDAY = 2, THURSDAY = 3, FRIDAY = 4, SATURDAY = 5, SUNDAY = 6
     * hour: in format of 0 - 23
     * */
    public static JSONArray sortByDayAndHour(String jsonString, final int day, final int hour) {
        try {
            JSONArray jsonArr = new JSONArray(jsonString);
            JSONArray sortedJsonArray = new JSONArray();

            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
            for (int i = 0; i < jsonArr.length(); i++) {
                jsonValues.add((JSONObject) jsonArr.get(i));
            }

            Collections.sort(jsonValues, new Comparator<JSONObject>() {
                private static final String KEY_NAME = "populartimes";
                private static final String DATA_KEY_NAME = "data";

                @Override
                public int compare(JSONObject a, JSONObject b) {
                    int valA;
                    int valB;

                    try {
                        valA = (int) a.getJSONArray(KEY_NAME).getJSONObject(day).getJSONArray(DATA_KEY_NAME).get(hour);
                        valB = (int) b.getJSONArray(KEY_NAME).getJSONObject(day).getJSONArray(DATA_KEY_NAME).get(hour);
                    } catch (JSONException e) {
                        valB = -1;
                        valA = -1;
                        e.printStackTrace();
                    }

                    return valB - valA;
                }
            });

            for (int i = 0; i < jsonArr.length(); i++) {
                sortedJsonArray.put(jsonValues.get(i));
            }

            return sortedJsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ContentValues[] getVenueContentValuesFromJsonString(String populartimesJsonString, Context context) throws JSONException {

        JSONArray populartimesJsonArray = new JSONArray(populartimesJsonString);
        return getVenueContentValuesFromJson(populartimesJsonArray, context);
    }

    public static ContentValues[] getVenueContentValuesFromJson(JSONArray populartimesJsonArray, Context context) throws JSONException {

        ContentValues[] contentValues = new ContentValues[populartimesJsonArray.length()];

        Log.d("WhatsHotJsonUtils: ", "populartimesJsonArray.length() for venues: " + populartimesJsonArray.length());
        for (int i = 0; i < populartimesJsonArray.length(); i++) {
            Log.d("WhatsHotJsonUtils: ", "iterate through populartimesJsonArray for venues: " + i);
            ContentValues cv = new ContentValues();

            JSONObject currentItemInPopularTimesJsonArray = (JSONObject) populartimesJsonArray.get(i);
//            Log.d("WhatsHotJsonUtils: ", "currentItemInPopularTimesJsonArray for venues: " + currentItemInPopularTimesJsonArray);
//            Log.d("WhatsHotJsonUtils: ", "currentItemInPopularTimesJsonArray.getString(WhatsHotJsonContract.VENUE_ID) for venues: " + currentItemInPopularTimesJsonArray.getString(WhatsHotJsonContract.VENUE_ID));

            cv.put(PopularTimesContract.VenueEntry.COLUMN_VENUE_ID, currentItemInPopularTimesJsonArray.getString(WhatsHotJsonContract.VENUE_ID));
            cv.put(PopularTimesContract.VenueEntry.COLUMN_NAME, currentItemInPopularTimesJsonArray.getString(WhatsHotJsonContract.NAME));
            cv.put(PopularTimesContract.VenueEntry.COLUMN_ADDRESS, currentItemInPopularTimesJsonArray.getString(WhatsHotJsonContract.ADDRESS));
            String internationalPhoneNumber = context.getString(R.string.no_phone_number);
            try {
                internationalPhoneNumber = currentItemInPopularTimesJsonArray.getString(WhatsHotJsonContract.INTERNATIONAL_PHONE_NUMBER);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cv.put(PopularTimesContract.VenueEntry.COLUMN_INTERNATIONAL_PHONE_NUMBER, internationalPhoneNumber);
            cv.put(PopularTimesContract.VenueEntry.COLUMN_RATING, currentItemInPopularTimesJsonArray.getString(WhatsHotJsonContract.RATING));
            cv.put(PopularTimesContract.VenueEntry.COLUMN_RATING_N, currentItemInPopularTimesJsonArray.getString(WhatsHotJsonContract.RATING_N));
            cv.put(PopularTimesContract.VenueEntry.COLUMN_LAT, currentItemInPopularTimesJsonArray.getJSONObject(WhatsHotJsonContract.COORDINATES).getDouble(WhatsHotJsonContract.LAT));
            cv.put(PopularTimesContract.VenueEntry.COLUMN_LNG, currentItemInPopularTimesJsonArray.getJSONObject(WhatsHotJsonContract.COORDINATES).getDouble(WhatsHotJsonContract.LNG));

            JSONArray typesJsonArray = currentItemInPopularTimesJsonArray.getJSONArray(WhatsHotJsonContract.TYPES);
            String typesString = "";
            Log.d("WhatsHotJsonUtils: ", "typesJsonArray.length(): " + typesJsonArray.length());
            for (int j = 0; j < typesJsonArray.length(); j++) {
                Log.d("WhatsHotJsonUtils: ", "iterate through typesJsonArray: " + i);
                if (j < typesJsonArray.length() - 1) {
                    typesString += "-" + typesJsonArray.get(j) + "-,";
                } else {
                    typesString += "-" + typesJsonArray.get(j) + "-";
                }
            }
            Log.d("WhatsHotJsonUtils: ", "typesString: " + typesString);
            cv.put(PopularTimesContract.VenueEntry.COLUMN_TYPES, typesString);

            contentValues[i] = cv;
        }

        return contentValues;
    }

    public static ContentValues[] getHoursContentValuesFromJsonString(String populartimesJsonString) throws JSONException {
        JSONArray populartimesJsonArray = new JSONArray(populartimesJsonString);
        return getHoursContentValuesFromJson(populartimesJsonArray);
    }

    public static ContentValues[] getHoursContentValuesFromJson(JSONArray populartimesJsonArray) throws JSONException {

        final int hoursInDay = 24;
        final int daysInWeek = 7;
        ContentValues[] contentValues = new ContentValues[populartimesJsonArray.length() * hoursInDay * daysInWeek];
        Log.d("WhatsHotJsonUtils: ", "populartimesJsonArray.length() for hours: " + populartimesJsonArray.length());

        for (int i = 0; i < populartimesJsonArray.length(); i++) {
            Log.d("WhatsHotJsonUtils: ", "iterate through populartimesJsonArray for hours: " + i);

            JSONObject currentItemInPopularTimesJsonArray = (JSONObject) populartimesJsonArray.get(i);

//            Log.d("WhatsHotJsonUtils: ", "currentItemInPopularTimesJsonArray for hours: " + currentItemInPopularTimesJsonArray);

            String venue_id = currentItemInPopularTimesJsonArray.getString(WhatsHotJsonContract.VENUE_ID);

            Log.d("WhatsHotJsonUtils: ", "currentItemInPopularTimesJsonArray.getString(WhatsHotJsonContract.VENUE_ID) for hours: " + venue_id);

            for (int day = 0; day < 7; day++) {
                for (int hour = 0; hour < 24; hour++) {
                    ContentValues cv = new ContentValues();
                    cv.put(PopularTimesContract.VenueEntry.VenueHoursEntry.COLUMN_VENUE_ID, venue_id);
                    cv.put(PopularTimesContract.VenueEntry.VenueHoursEntry.COLUMN_DAY, day);
                    cv.put(PopularTimesContract.VenueEntry.VenueHoursEntry.COLUMN_HOUR, hour);

                    JSONObject dayJsonObject = (JSONObject) currentItemInPopularTimesJsonArray.getJSONArray(WhatsHotJsonContract.POPULARTIMES).get(day);
                    String popularity = Integer.toString((int) dayJsonObject.getJSONArray(WhatsHotJsonContract.DATA_IN_POPULARTIMES).get(hour));
                    cv.put(PopularTimesContract.VenueEntry.VenueHoursEntry.COLUMN_POPULARITY, popularity);
                    contentValues[i * hoursInDay * daysInWeek + day * hoursInDay + hour] = cv;
                }
            }
        }

        return contentValues;

    }


}
