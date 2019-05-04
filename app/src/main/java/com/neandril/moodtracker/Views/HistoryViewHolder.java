package com.neandril.moodtracker.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neandril.moodtracker.Models.Mood;
import com.neandril.moodtracker.R;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public ImageButton button;
    public RelativeLayout relativeLayout;

    public HistoryViewHolder (View itemview) {
        super(itemview);

        relativeLayout = itemview.findViewById(R.id.hist_main_layout);
        textView = itemview.findViewById(R.id.hist_textView);
        button = itemview.findViewById(R.id.hist_commentBtn);
    }

    /**
     * Bind the view with a mood object
     * @param mood retrieve data to be displayed about moods
     */
    public void bind(final Mood mood){

    }
}
