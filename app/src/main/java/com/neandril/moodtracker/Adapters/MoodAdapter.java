package com.neandril.moodtracker.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neandril.moodtracker.Models.Mood;
import com.neandril.moodtracker.R;
import com.neandril.moodtracker.Views.RecyclerViewHolder;

import java.util.List;

/**
 * Adapter class for RecyclerView
 */

public class MoodAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    // Store Moods infos into a list
    List<Mood> mMoodList;

    public static final String TAG = "MainActivity";

    // Constructor with a list as a param
    public MoodAdapter(List<Mood> list) {
        this.mMoodList = list;
    }

    /**
     * Create ViewHolders, and inflate the view
     * @param viewGroup - View displaying the list (according to the xml file)
     * @param itemType - items
     * @return
     */
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mood,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    /**
     * Bind ViewHolder, and fill all that's necessary
     * @param viewHolder - the viewholder
     * @param position - identifier of the position of the view inside the viewholder
     */
    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        Mood myObject = mMoodList.get(position);
        viewHolder.bind(myObject);
    }

    /**
     * Mandatory method inside the adapter, used to know
     * how many items are in the recyclerview
     * @return
     */
    @Override
    public int getItemCount() {
        return mMoodList.size();
    }
}
