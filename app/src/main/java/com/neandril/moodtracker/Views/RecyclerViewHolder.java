package com.neandril.moodtracker.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.neandril.moodtracker.Models.Mood;
import com.neandril.moodtracker.R;

/**
 * ViewHolder class for the RecyclerView
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageView;
    private RelativeLayout mRelativeLayout;

    // Init the view for one cell ...
    public RecyclerViewHolder(View itemView) {
        super(itemView);

        // ... with objects (in xml)
        mRelativeLayout = itemView.findViewById(R.id.item_layout);
        mImageView = itemView.findViewById(R.id.smiley_icon);
    }

    /**
     * Bind the view with a mood object
     * @param mood retrieve data to be displayed about moods
     */
    public void bind(final Mood mood){
        mImageView.setImageResource(mood.getIcon());
        mRelativeLayout.setBackgroundResource(mood.getBackground());
    }
}
