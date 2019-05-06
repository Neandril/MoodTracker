package com.neandril.moodtracker.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.neandril.moodtracker.Activities.HistoryActivity;
import com.neandril.moodtracker.Models.Mood;
import com.neandril.moodtracker.R;
import com.neandril.moodtracker.Views.HistoryViewHolder;
import com.neandril.moodtracker.Views.RecyclerViewHolder;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    // Store Moods infos into a list
    private ArrayList<Mood> mMoodList;

    private Activity mActivity;

    private double deviceWidth;
    private double deviceHeight;

    // Constructor with a list as a param
    public HistoryAdapter(Activity activity, ArrayList<Mood> list) {
        mActivity = activity;
        this.mMoodList = list;
    }

    /**
     * Create ViewHolders, and inflate the view
     * @param viewGroup - View displaying the list (according to the xml file)
     * @param itemType - items
     * @return
     */
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history,viewGroup,false);
        return new HistoryViewHolder(view);
    }

    /**
     * Bind ViewHolder, and fill all that's necessary
     * @param viewHolder - the viewholder
     * @param position - identifier of the position of the view inside the viewholder
     */
    @Override
    public void onBindViewHolder(@NonNull final HistoryViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        Mood mood = mMoodList.get(position);

        // Get the metrics of the device
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        deviceWidth = displayMetrics.widthPixels;
        deviceHeight = displayMetrics.heightPixels;

        // Value to set the width per percent of the screen
        final double [] textViewWidthComputing = {1, 0.8, 0.6, 0.4,  0.25};

        ViewGroup.LayoutParams param = viewHolder.relativeLayout.getLayoutParams();
        param.width = (int) (deviceWidth * textViewWidthComputing[mood.getId()]);
        viewHolder.relativeLayout.setLayoutParams(param);

        // Set the background
        viewHolder.textView.setBackgroundResource(mood.getBackground());

        // Set the "x days ago" inside the textview
        String array[] = mActivity.getResources().getStringArray(R.array.history_array);
        viewHolder.textView.setText(array[position]);

        Log.e("HistoryAdapter", "DATE : " + mMoodList.get(position).getDate());

        // Display the button if a comment is stored
        String comment = mMoodList.get(position).getComment();
        if (comment == null || comment.isEmpty()) {
            viewHolder.button.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.button.setVisibility(View.VISIBLE);
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mActivity, mMoodList.get(position).getComment(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * Mandatory method inside the adapter, used to know
     * how many items are in the recyclerview
     * @return
     */
    @Override
    public int getItemCount() {
        if (mMoodList != null) {
            return mMoodList.size();
        } else {
            return 0;
        }
    }
}
