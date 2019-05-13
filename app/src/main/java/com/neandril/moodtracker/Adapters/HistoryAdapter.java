package com.neandril.moodtracker.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.neandril.moodtracker.Models.Mood;
import com.neandril.moodtracker.R;
import com.neandril.moodtracker.Views.HistoryViewHolder;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    // Store Moods infos into a list
    private ArrayList<Mood> mMoodList;

    private Activity mActivity;

    // Constructor with a list as a param
    public HistoryAdapter(Activity activity, ArrayList<Mood> list) {
        mActivity = activity;
        this.mMoodList = list;
    }

    /**
     * Create ViewHolders, and inflate the view
     * @param viewGroup - View displaying the list (according to the xml file)
     * @param itemType - items
     * @return return the viewholder
     */
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
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
        double deviceWidth = displayMetrics.widthPixels;
        double deviceHeight = displayMetrics.heightPixels;

        // Get the height of the action bar
        int [] textSizeAttr = new int[] {R.attr.actionBarSize};
        TypedArray a = mActivity.obtainStyledAttributes(new TypedValue().data, textSizeAttr);
        int actionbarHeight = a.getDimensionPixelSize(0,0);
        a.recycle();
        // Total visible height equals to deviceHeight minus actionbarHeight
        deviceHeight = deviceHeight - actionbarHeight;

        // Value to set the width per percent of the screen
        final double [] textViewWidthComputing = {1, 0.8, 0.6, 0.4, 0.25};

        ViewGroup.LayoutParams param = viewHolder.relativeLayout.getLayoutParams();
        param.width = (int) (deviceWidth * textViewWidthComputing[mood.getId()]);
        param.height = (int) deviceHeight / 7;
        viewHolder.relativeLayout.setLayoutParams(param);

        // Set the background
        viewHolder.textView.setBackgroundResource(mood.getBackground());

        // Set the "x days ago" inside the textview
        String[] array = mActivity.getResources().getStringArray(R.array.history_array);
        viewHolder.textView.setText(array[position]);

        // Display the button if a comment is stored, and show it inside a Toast
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
     * @return return items count
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
