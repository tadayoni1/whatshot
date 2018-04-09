package com.example.android.whatshot;

import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.whatshot.data.PopularTimesContract;
import com.example.android.whatshot.utilities.DataProcessingUtils;

/**
 * Created by Pedram on 3/18/2018.
 */

// TODO: Kenda, Hamed

public class PopularTimesAdapter extends RecyclerView.Adapter<PopularTimesAdapter.PopularTimesAdapterViewHolder> {

    // Class variable that holds  the Context
    private Context mContext;

    private Cursor mCursor;

    public static final String TAG = "PopularTimesAdapter";

    /**
     * Constructor for the PopularTimesAdapter that initializes the Context.
     */
    public PopularTimesAdapter(Context mContext) {
        this.mContext = mContext;
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


        holder.establishmentImageView.setContentDescription(type);
        holder.establishmentImageView.setImageResource(DataProcessingUtils.getStringId(type));

        holder.establishmentNameView.setText(mCursor.getString(mCursor.getColumnIndex(PopularTimesContract.VenueEntry.COLUMN_NAME)));
        holder.rankView.setText(mCursor.getString(mCursor.getColumnIndex(PopularTimesContract.VenueEntry.VenueHoursEntry.COLUMN_POPULARITY)));
        holder.rantingView.setText(mCursor.getString(mCursor.getColumnIndex(PopularTimesContract.VenueEntry.COLUMN_RATING)));
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

        public PopularTimesAdapterViewHolder(View itemView) {
            super(itemView);
            establishmentNameView = itemView.findViewById(R.id.establishment_name);
            rankView = itemView.findViewById(R.id.rankTextView);
            rantingView = itemView.findViewById(R.id.ratingsTextView);
            establishmentImageView = itemView.findViewById(R.id.establishment_image);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

}