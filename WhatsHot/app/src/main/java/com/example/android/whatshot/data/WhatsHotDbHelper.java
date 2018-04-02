package com.example.android.whatshot.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.whatshot.data.PopularTimesContract.VenueEntry;
import com.example.android.whatshot.data.PopularTimesContract.VenueEntry.VenueHoursEntry;

public class WhatsHotDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "whatshot.db";


    public WhatsHotDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_VENUE_TABLE =

                "CREATE TABLE " + VenueEntry.TABLE_NAME + " (" +

                        VenueEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        VenueEntry.COLUMN_VENUE_ID       + " TEXT NOT NULL, "                 +
                        VenueEntry.COLUMN_NAME       + " TEXT NOT NULL, "                 +
                        VenueEntry.COLUMN_ADDRESS       + " TEXT NOT NULL, "                 +
                        VenueEntry.COLUMN_INTERNATIONAL_PHONE_NUMBER       + " TEXT NOT NULL, "                 +
                        VenueEntry.COLUMN_RATING       + " REAL NOT NULL, "                 +
                        VenueEntry.COLUMN_RATING_N       + " INTEGER NOT NULL, "                 +
                        VenueEntry.COLUMN_LAT       + " REAL NOT NULL, "                 +
                        VenueEntry.COLUMN_LNG       + " REAL NOT NULL, "                 +
                        VenueEntry.COLUMN_TYPES       + " TEXT NOT NULL, "                 +

                        " UNIQUE (" + VenueEntry.COLUMN_VENUE_ID + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(SQL_CREATE_VENUE_TABLE);

        final String SQL_CREATE_HOURS_TABLE =

                "CREATE TABLE " + VenueHoursEntry.TABLE_NAME + " (" +

                        VenueHoursEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        VenueHoursEntry.COLUMN_VENUE_ID       + " TEXT NOT NULL, "                 +
                        VenueHoursEntry.COLUMN_DAY       + " INTEGER NOT NULL, "                 +
                        VenueHoursEntry.COLUMN_HOUR       + " INTEGER NOT NULL, "                 +
                        VenueHoursEntry.COLUMN_POPULARITY       + " INTEGER NOT NULL"             +
                        ");";
        sqLiteDatabase.execSQL(SQL_CREATE_HOURS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VenueEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VenueHoursEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}