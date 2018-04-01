package com.example.android.whatshot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.whatshot.utilities.NetworkUtils;
import com.example.android.whatshot.utilities.WhatsHotJsonUtils;

import org.json.JSONArray;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

// TODO: Kenda, Hamed

public class MainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<JSONArray> {

    private TextView mShowJson;

    public static final int POPULARTIMES_SEARCH_LOADER_ID = 22;

    private static final String SEARCH_QUERY_URL_EXTRA = "query";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShowJson = (TextView) findViewById(R.id.textView_show_json);


        /*
         * Initialize the loader
         */
        getSupportLoaderManager().initLoader(POPULARTIMES_SEARCH_LOADER_ID, null, this);
        makeGithubSearchQuery();
    }

    @Override
    public Loader<JSONArray> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<JSONArray>(this) {
            JSONArray mPopularTimesJson;

            @Override
            protected void onStartLoading() {
                /* If no arguments were passed, we don't have a query to perform. Simply return. */
                if (args == null) {
                    return;
                }

                // If mPopularTimesJson is not null, deliver that result. Otherwise, force a load
                if (mPopularTimesJson != null)
                    deliverResult(mPopularTimesJson);
                else
                    forceLoad();
            }

            @Override
            public JSONArray loadInBackground() {
                /* Extract the search query from the args using our constant */
                String searchQueryUrlString = args.getString(SEARCH_QUERY_URL_EXTRA);

                Log.d(getClass().toString(), "PPPP searchQueryUrlString in Loader: " + searchQueryUrlString.toString());
                /* If the user didn't enter anything, there's nothing to search for */
                if (searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString)) {
                    return null;
                }

                /* Parse the URL from the passed in String and perform the search */
                try {
                    URL populartimesUrl = new URL(searchQueryUrlString);
                    Log.d(getClass().toString(), "PPPP populartimesUrl in Loader: " + populartimesUrl);
                    String populartimesSearchResults = NetworkUtils.getResponseFromHttpUrl(populartimesUrl);
                    Log.d(getClass().toString(), "PPPP populartimesSearchResults in Loader: " + populartimesSearchResults);
                    return WhatsHotJsonUtils.sortByDayAndHour(populartimesSearchResults, 0, 0);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            // Override deliverResult and store the data in mGithubJson
            // Call super.deliverResult after storing the data
            @Override
            public void deliverResult(JSONArray data) {
                mPopularTimesJson = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<JSONArray> loader, JSONArray data) {
        /*
         * If the results are null, we assume an error has occurred. There are much more robust
         * methods for checking errors, but we wanted to keep this particular example simple.
         */
        if (null != data) {
            mShowJson.setText(data.toString());
            Log.d(getClass().toString(), "PPPP onLoadFinished data: " + data.toString());
        }
    }

    @Override
    public void onLoaderReset(Loader<JSONArray> loader) {

    }


    private void makeGithubSearchQuery() {
        URL populartimesSearchUrl = NetworkUtils.buildUrl("");

        Log.d(getClass().toString(), "PPPP URL: " + populartimesSearchUrl.toString());
        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, populartimesSearchUrl.toString());
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> populartimesSearchLoader = loaderManager.getLoader(POPULARTIMES_SEARCH_LOADER_ID);
        if (populartimesSearchLoader == null) {
            loaderManager.initLoader(POPULARTIMES_SEARCH_LOADER_ID, queryBundle, this);
        } else {
            loaderManager.restartLoader(POPULARTIMES_SEARCH_LOADER_ID, queryBundle, this);
        }
    }
}
