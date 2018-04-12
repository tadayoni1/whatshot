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
public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private TextView mLocationNameTV, mTypeTV, mPopularityTV, mAddressTV, mPhoneTV, mRatingCount;
    private TextView mMonTV, mTueTV, mWedTV,mThuTV,mFriTV,mSatTV,mSunTV;

    private RatingBar mRatingRB;


    public static final int INDEX_VENUE_NAME = 2;
    public static final int INDEX_VENUE_ADDRESS = 3;
    public static final int INDEX_VENUE_PHONE = 4;
    public static final int INDEX_VENUE_RATING = 5;
    public static final int INDEX_VENUE_RATING_COUNT = 6;
    public static final int INDEX_VENUE_LAT = 7;
    public static final int INDEX_VENUE_LNG = 8;
    public static final int INDEX_VENUE_TYPE = 9;


    private static final int ID_VENUE_LOADER = 447;
    private static final int ID_VENUE_DETAIL_LOADER = 448;

    private Uri mVenueUri;
    private Uri mVenueDetailUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mLocationNameTV = (TextView) findViewById(R.id.tv_location_name);
        mPhoneTV = (TextView) findViewById(R.id.tv_phone);
        mAddressTV = (TextView) findViewById(R.id.tv_address);
        mTypeTV = (TextView) findViewById(R.id.tv_type);
        mPopularityTV = (TextView) findViewById(R.id.tv_popularity);
        mRatingCount=(TextView)findViewById(R.id.tv_rating_count);
        mRatingRB = (RatingBar) findViewById(R.id.rb_rating);

        mMonTV = (TextView) findViewById(R.id.tv_monday);
        mTueTV = (TextView) findViewById(R.id.tv_tuesday);
        mWedTV = (TextView) findViewById(R.id.tv_wednesday);
        mThuTV = (TextView) findViewById(R.id.tv_thursday);
        mFriTV = (TextView) findViewById(R.id.tv_friday);
        mSatTV = (TextView) findViewById(R.id.tv_saturday);
        mSunTV = (TextView) findViewById(R.id.tv_sunday);


        mVenueUri = Uri.parse(getIntent().getStringExtra(MainActivity.DETAIL_ACTIVITY_EXTRA_VENUE));
        mVenueDetailUri = Uri.parse(getIntent().getStringExtra(MainActivity.DETAIL_ACTIVITY_EXTRA_VENUE_DETAILS));

        if (mVenueUri == null || mVenueDetailUri == null)
            throw new NullPointerException("URI for DetailActivity cannot be null");
        getSupportLoaderManager().initLoader(ID_VENUE_LOADER, null, this);
        getSupportLoaderManager().initLoader(ID_VENUE_DETAIL_LOADER, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_VENUE_LOADER:
                return new CursorLoader(this,
                        mVenueUri,
                        null,
                        null,
                        null,
                        null);
            case ID_VENUE_DETAIL_LOADER:
                return new CursorLoader(this,
                        mVenueDetailUri,
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

        switch (loader.getId()) {
            case ID_VENUE_LOADER:
                boolean cursorHasValidData = false;
                if (data != null && data.moveToFirst()) {
                    /* We have valid data, continue on to bind the data to the UI */
                    cursorHasValidData = true;
                }

                if (!cursorHasValidData) {
                    /* No data to display, simply return and do nothing */
                    return;
                }

                String venueName = data.getString(INDEX_VENUE_NAME);
                mLocationNameTV.setText(venueName);

                String venuePhone = "Phone:\n" + data.getString(INDEX_VENUE_PHONE);
                mPhoneTV.setText(venuePhone);

                String venueAddress = "Address:\n" + data.getString(INDEX_VENUE_ADDRESS);
                mAddressTV.setText(venueAddress);

                String[] venueTypes = data.getString(INDEX_VENUE_TYPE).split(",");
                String venueType = "Type: " + venueTypes[0].replaceAll("[^a-zA-Z0-9]", "");
                mTypeTV.setText(venueType);

                String todayPopularity=data.getString(data.getColumnIndex(PopularTimesContract.VenueEntry.VenueHoursEntry.COLUMN_POPULARITY));
                mPopularityTV.setText(todayPopularity);

                String ratingCount = data.getString(INDEX_VENUE_RATING_COUNT) + " users rated this venue.";
                mRatingCount.setText(ratingCount);

                float rating = Float.parseFloat(data.getString(INDEX_VENUE_RATING));
                mRatingRB.setRating(rating);

                break;
            case ID_VENUE_DETAIL_LOADER:

                if(data==null){
                    Log.d("OtherThings","No data");
                    return;
                }

                int monTotal=0;
                int tueTotal=0;
                int wedTotal=0;
                int thuTotal=0;
                int friTotal=0;
                int satTotal=0;
                int sunTotal=0;


                while (data.moveToNext()) {
                    if(data.getString(2).equals("0")){
                        monTotal+=Integer.valueOf(data.getString(4));
                    }
                    if(data.getString(2).equals("1")){
                        tueTotal+=Integer.valueOf(data.getString(4));
                    }
                    if(data.getString(2).equals("2")){
                        wedTotal+=Integer.valueOf(data.getString(4));
                    }
                    if(data.getString(2).equals("3")){
                        thuTotal+=Integer.valueOf(data.getString(4));
                    }
                    if(data.getString(2).equals("4")){
                        friTotal+=Integer.valueOf(data.getString(4));
                    }
                    if(data.getString(2).equals("5")){
                        satTotal+=Integer.valueOf(data.getString(4));
                    }
                    if(data.getString(2).equals("6")) {
                        sunTotal += Integer.valueOf(data.getString(4));
                    }
                }
                mMonTV.setText(String.valueOf(monTotal));
                mTueTV.setText(String.valueOf(tueTotal));
                mWedTV.setText(String.valueOf(wedTotal));
                mThuTV.setText(String.valueOf(thuTotal));
                mFriTV.setText(String.valueOf(friTotal));
                mSatTV.setText(String.valueOf(satTotal));
                mSunTV.setText(String.valueOf(sunTotal));


                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
