package com.example.android.whatshot;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pedram on 3/18/2018.
 */

// TODO: Kenda, Hamed

public class PopularTimesAdapter extends RecyclerView.Adapter<PopularTimesAdapter.PopularTimesAdapterViewHolder> {

    @Override
    public PopularTimesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PopularTimesAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PopularTimesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public PopularTimesAdapterViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
