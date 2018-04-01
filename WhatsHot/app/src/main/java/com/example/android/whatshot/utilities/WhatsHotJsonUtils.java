package com.example.android.whatshot.utilities;

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

}
