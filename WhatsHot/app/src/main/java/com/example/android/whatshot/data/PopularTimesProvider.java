package com.example.android.whatshot.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.whatshot.data.PopularTimesContract.VenueEntry;
import com.example.android.whatshot.data.PopularTimesContract.VenueEntry.VenueHoursEntry;

import java.util.List;

/**
 * Created by soheil on 4/1/18.
 */

public class PopularTimesProvider extends ContentProvider {

    public static final int CODE_VENUES = 100;
    public static final int CODE_VENUE_DETAILS = 101;
    public static final int CODE_VENUE_WITH_DAY_AND_HOUR = 102;


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private WhatsHotDbHelper mOpenHelper;

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PopularTimesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, PopularTimesContract.PATH_VENUE, CODE_VENUES);

        matcher.addURI(authority, PopularTimesContract.PATH_VENUE_DETAILS, CODE_VENUE_DETAILS);

        matcher.addURI(authority, PopularTimesContract.PATH_VENUE + "/#/#", CODE_VENUE_WITH_DAY_AND_HOUR);

        return matcher;


    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new WhatsHotDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsInserted = 0;


        switch (sUriMatcher.match(uri)) {

            case CODE_VENUES:
                Log.d(getClass().toString(), "BulkInsert for " + CODE_VENUES);
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(PopularTimesContract.VenueEntry.TABLE_NAME, null, value);
//                        Log.d(getClass().toString(), "BulkInsert _id=" + _id);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            case CODE_VENUE_DETAILS:
                Log.d(getClass().toString(), "BulkInsert for " + CODE_VENUE_DETAILS);
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(PopularTimesContract.VenueEntry.VenueHoursEntry.TABLE_NAME, null, value);
//                        Log.d(getClass().toString(), "BulkInsert _id=" + _id);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;


            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)) {
            case CODE_VENUES: {
                Log.d(getClass().toString(), "query for CODE_VENUES");
                String venue_id = uri.getLastPathSegment();

                String[] selectionArguments = new String[]{venue_id};

                SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

                qb.setTables(VenueEntry.TABLE_NAME + " JOIN " + VenueHoursEntry.TABLE_NAME +
                        " ON " + VenueHoursEntry.TABLE_NAME + "." + VenueHoursEntry.COLUMN_VENUE_ID +
                        " = " + VenueEntry.TABLE_NAME + "." + VenueEntry.COLUMN_VENUE_ID);

                cursor = mOpenHelper.getReadableDatabase().query(
                        VenueEntry.TABLE_NAME,

                        projection,
                        VenueEntry.COLUMN_VENUE_ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        null);
                break;
            }

            case CODE_VENUE_WITH_DAY_AND_HOUR: {
                Log.d(getClass().toString(), "query for CODE_VENUE_WITH_DAY_AND_HOUR");

                List<String> pathSegment = uri.getPathSegments();
                String day = pathSegment.get(1);
                Log.d(getClass().toString(), "pathSegment day: " + day);
                String hour = pathSegment.get(2);
                Log.d(getClass().toString(), "pathSegment hour: " + hour);
                String[] selectionArguments = new String[]{day, hour};

                SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
                String tables = VenueEntry.TABLE_NAME + " INNER JOIN " + VenueHoursEntry.TABLE_NAME +
                        " ON " + VenueHoursEntry.TABLE_NAME + "." + VenueHoursEntry.COLUMN_VENUE_ID +
                        " = " + VenueEntry.TABLE_NAME + "." + VenueEntry.COLUMN_VENUE_ID;
                qb.setTables(tables);

                Log.d(getClass().toString(), "query builder tables: " + tables);

                String whereStatement = "";
                whereStatement = VenueHoursEntry.COLUMN_DAY + " = '" + day + "'" + " AND " + VenueHoursEntry.COLUMN_HOUR + " = '" + hour + "'";;
                qb.appendWhere(whereStatement);
                Log.d(getClass().toString(), "query builder whereStatements: " + whereStatement);
                cursor = qb.query(mOpenHelper.getReadableDatabase(),
                        projection,
                        null,
                        null,
                        null,
                        null,
                        VenueHoursEntry.COLUMN_POPULARITY + " DESC"
                );

                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;

        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)) {

            case CODE_VENUES:
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        VenueEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;

            case CODE_VENUE_DETAILS:
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        VenueEntry.VenueHoursEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        /* If we actually deleted any rows, notify that a change has occurred to this URI */
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
