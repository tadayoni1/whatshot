package com.example.android.whatshot;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.whatshot.data.PopularTimesContract;
import com.example.android.whatshot.utilities.DataProcessingUtils;

import static com.example.android.whatshot.data.PopularTimesContract.VenueEntry.buildVenueUriWithDayAndHourAndVenueId;

/**
 * Created by Pedram on 3/18/2018.
 */

// TODO: Kenda, Hamed

public class PopularTimesAdapter extends RecyclerView.Adapter<PopularTimesAdapter.PopularTimesAdapterViewHolder> {

    // Class variable that holds  the Context
    private Context mContext;

    private Cursor mCursor;

    public static final String TAG = "PopularTimesAdapter";

    /*
    * Below, we've defined an interface to handle clicks on items within this Adapter. In the
    * constructor of our PopularTimesAdapter, we receive an instance of a class that has implemented
    * said interface. We store that instance in this variable to call the onClick method whenever
    * an item is clicked in the list.
    */
    final private PopularTimesAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface PopularTimesAdapterOnClickHandler {
        void onClick(int position);
    }

    /**
     * Constructor for the PopularTimesAdapter that initializes the Context.
     */
    public PopularTimesAdapter(Context mContext, PopularTimesAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        mClickHandler = clickHandler;
    }

    @Override
    public PopularTimesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        // Inflate the main_list_item to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.main_list_item, parent, false);

        return new PopularTimesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PopularTimesAdapterViewHolder holder, int position) {


        mCursor.moveToPosition(position);

        String type = DataProcessingUtils.getTopVenueType(mCursor.getString(
                mCursor.getColumnIndex(PopularTimesContract.VenueEntry.COLUMN_TYPES)));


        holder.establishmentImageView.setContentDescription(mContext.getString(DataProcessingUtils.getStringPlaceId(type)));
        holder.establishmentImageView.setImageResource(DataProcessingUtils.getStringIconId(type));

        holder.establishmentNameView.setText(mCursor.getString(mCursor.getColumnIndex(PopularTimesContract.VenueEntry.COLUMN_NAME)));
        holder.rankView.setText(mCursor.getString(mCursor.getColumnIndex(PopularTimesContract.VenueEntry.VenueHoursEntry.COLUMN_POPULARITY)));
        holder.rantingView.setText(mCursor.getString(mCursor.getColumnIndex(PopularTimesContract.VenueEntry.COLUMN_RATING)));
        holder.venue_id = mCursor.getString(mCursor.getColumnIndex(PopularTimesContract.VenueEntry.COLUMN_VENUE_ID));
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    class PopularTimesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView establishmentNameView;
        final TextView rankView;
        final TextView rantingView;
        final ImageView establishmentImageView;
        String venue_id;

        public PopularTimesAdapterViewHolder(View itemView) {
            super(itemView);
            establishmentNameView = itemView.findViewById(R.id.establishment_name);
            rankView = itemView.findViewById(R.id.rankTextView);
            rantingView = itemView.findViewById(R.id.ratingsTextView);
            establishmentImageView = itemView.findViewById(R.id.establishment_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            Uri uriForItemClicked = buildVenueUriWithDayAndHourAndVenueId(0, 12, venue_id);
            detailIntent.setData(uriForItemClicked);
            mContext.startActivity(detailIntent);
        }
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

}