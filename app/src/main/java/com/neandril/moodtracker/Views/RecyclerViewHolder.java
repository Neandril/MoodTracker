package com.neandril.moodtracker.Views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neandril.moodtracker.Models.Mood;
import com.neandril.moodtracker.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;
    private ImageView mImageView;
    private RelativeLayout mRelativeLayout;

    public static final String TAG = "MainActivity";

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        mRelativeLayout = itemView.findViewById(R.id.item_layout);
        mTextView = itemView.findViewById(R.id.mTextView);
        mImageView = itemView.findViewById(R.id.smiley_icon);
    }

    public void bind(final Mood mood){
        mTextView.setText(mood.getText());
        mImageView.setImageResource(mood.getIcon());
        mRelativeLayout.setBackgroundResource(mood.getBackground());

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Id : " + mood.getId() + " - Mood : " + mood.getText());

            }
        });
    }
}
