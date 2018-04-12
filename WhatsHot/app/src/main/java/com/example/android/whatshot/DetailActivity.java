package com.example.android.whatshot;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.whatshot.data.PopularTimesContract;

// TODO: Yany, Kerry
public class DetailActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<Cursor> {
    private TextView mLocationNameTV, mTypeTV, mPopularityTV, mAddressTV,mPhoneTV, mMonTV;

    private RatingBar mRatingRB;


    public static final int INDEX_VENUE_NAME=2;
    public static final int INDEX_VENUE_ADDRESS=3;
    public static final int INDEX_VENUE_PHONE=4;
    public static final int INDEX_VENUE_RATING=5;

    public static final int INDEX_VENUE_LAT=7;
    public static final int INDEX_VENUE_LNG=8;
    public static final int INDEX_VENUE_TYPE=9;


    private static final int ID_DETAIL_LOADER = 447;

    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mLocationNameTV=(TextView)findViewById(R.id.tv_location_name);
        mPhoneTV=(TextView)findViewById(R.id.tv_phone);
        mAddressTV=(TextView)findViewById(R.id.tv_address);
        mTypeTV=(TextView)findViewById(R.id.tv_type);

        mPopularityTV=(TextView)findViewById(R.id.popularity);
        mRatingRB = (RatingBar)findViewById(R.id.ratings);
        mMonTV=(TextView)findViewById(R.id.tv_monday);

       mUri = getIntent().getData();

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

        String venueName=data.getString(INDEX_VENUE_NAME);
        mLocationNameTV.setText(venueName);

        String venuePhone="Phone:\n"+data.getString(INDEX_VENUE_PHONE);
        mPhoneTV.setText(venuePhone);

        String venueAddress="Address:\n"+data.getString(INDEX_VENUE_ADDRESS);
        mAddressTV.setText(venueAddress);

        String[] venueTypes=data.getString(INDEX_VENUE_TYPE).split(",");
        String venueType="Type: "+venueTypes[0].replaceAll("[^a-zA-Z0-9]","");
        mTypeTV.setText(venueType);

        String popularity=data.getString(data.getColumnIndex(PopularTimesContract.VenueEntry.COLUMN_RATING_N))+" users rated this venue.";
        mPopularityTV.setText(popularity);

        float rating= Float.parseFloat(data.getString(INDEX_VENUE_RATING));
        mRatingRB.setRating(rating);
        mMonTV.setText(data.getString(data.getColumnIndex(PopularTimesContract.VenueEntry.VenueHoursEntry.COLUMN_POPULARITY)));

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
