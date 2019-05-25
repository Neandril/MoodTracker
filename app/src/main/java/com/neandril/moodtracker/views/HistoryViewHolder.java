package com.neandril.moodtracker.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neandril.moodtracker.R;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    public final TextView textView;
    public final ImageButton button;
    public final RelativeLayout relativeLayout;

    public HistoryViewHolder (View itemview) {
        super(itemview);

        relativeLayout = itemview.findViewById(R.id.hist_main_layout);
        textView = itemview.findViewById(R.id.hist_textView);
        button = itemview.findViewById(R.id.hist_commentBtn);
    }
}
