package com.example.android.whatshot;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.whatshot.data.PopularTimesContract;

// TODO: Yany, Kerry
public class DetailActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<Cursor> {
    private TextView mLocationNameTV;
    private TextView mDateTV;
    private RatingBar mRankingRB;


    public static final String[] POPULARTIMES_DETAIL_PROJECTION = {
            PopularTimesContract.VenueEntry.COLUMN_NAME,
            PopularTimesContract.VenueEntry.COLUMN_ADDRESS,
            PopularTimesContract.VenueEntry.COLUMN_RATING,
            PopularTimesContract.VenueEntry.COLUMN_INTERNATIONAL_PHONE_NUMBER,
            PopularTimesContract.VenueEntry.COLUMN_TYPES,
            PopularTimesContract.VenueEntry.COLUMN_LAT,
            PopularTimesContract.VenueEntry.COLUMN_LNG,
            PopularTimesContract.VenueEntry.VenueHoursEntry.COLUMN_DAY,
            PopularTimesContract.VenueEntry.VenueHoursEntry.COLUMN_HOUR,
            PopularTimesContract.VenueEntry.VenueHoursEntry.COLUMN_POPULARITY,
            PopularTimesContract.VenueEntry.COLUMN_VENUE_ID

    };
    public static final int INDEX_VENUE_NAME=0;
    public static final int INDEX_VENUE_ADDRESS=1;
    public static final int INDEX_VENUE_RATING=2;
    public static final int INDEX_VENUE_PHONE=3;
    public static final int INDEX_VENUE_TYPE=4;
    public static final int INDEX_VENUE_LAT=5;
    public static final int INDEX_VENUE_LNG=6;
    public static final int INDEX_VENUE_DAY=7;
    public static final int INDEX_VENUE_HOUR=8;
    public static final int INDEX_VENUE_POPULARITY=9;
    public static final int INDEX_VENUE_ID=10;


    private static final int ID_DETAIL_LOADER = 447;

    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mLocationNameTV=(TextView)findViewById(R.id.location_name);
        mDateTV=(TextView)findViewById(R.id.date);
        mRankingRB = (RatingBar)findViewById(R.id.ratings);

       mUri = getIntent().getData();

        Bundle bundle=getIntent().getExtras();

        Log.d("detail activity", "Arrived Detail Activity"+mUri.getPath());
        if (mUri == null) throw new NullPointerException("URI for DetailActivity cannot be null");
        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER,null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_DETAIL_LOADER:
                return new CursorLoader(this,
                        mUri,
                        null,
                        null,
                        null,
                        null);
                default:
                    throw new RuntimeException("Loader Not Implemented: " + id);

        }


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        boolean cursorHasValidData = false;
        if (data != null && data.moveToFirst()) {
            /* We have valid data, continue on to bind the data to the UI */
            cursorHasValidData = true;
        }

        if (!cursorHasValidData) {
            /* No data to display, simply return and do nothing */
            return;
        }

        //String venueName=data.getString(INDEX_VENUE_NAME);
       // mLocationNameTV.setText(venueName);

        //String rating= data.getString(INDEX_VENUE_RATING);
 //       Log.i("Testing rating", rating);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
