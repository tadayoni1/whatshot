package com.example.android.whatshot.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by soheil on 4/1/18.
 */



public class PopularTimesContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.whatshot";


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_VENUE = "venues";


    public static final class VenueEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_VENUE)
                .build();

        public static final String TABLE_NAME = "venues";

        public static final String COLUMN_VENUE_ID = "venue_id";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_ADDRESS = "address";

        public static final String COLUMN_INTERNATIONAL_PHONE_NUMBER = "international_phone_number";

        public static final String COLUMN_RATING = "rating";

        public static final String COLUMN_RATING_N = "rating_n";

        public static final String COLUMN_LAT = "lat";

        public static final String COLUMN_LNG = "lng";

        public static final String COLUMN_TYPES = "types";

        public static Uri buildVenueUriDetail(String venue_id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(venue_id)
                    .build();
        }

        public static final class VenueHoursEntry implements BaseColumns {
            public static final String TABLE_NAME = "venue_hours";
            public static final String COLUMN_VENUE_ID = "venue_id";
            public static final String COLUMN_DAY = "day";
            public static final String COLUMN_HOUR = "hour";
            public static final String COLUMN_POPULARITY = "popularity";
        }

    }
}
