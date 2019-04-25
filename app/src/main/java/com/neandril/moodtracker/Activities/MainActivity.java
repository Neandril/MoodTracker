package com.neandril.moodtracker.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.neandril.moodtracker.Adapters.MoodAdapter;
import com.neandril.moodtracker.Helpers.ItemClickSupport;
import com.neandril.moodtracker.Models.Mood;
import com.neandril.moodtracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declarations
    private RecyclerView mRecyclerView;
    private List<Mood> mMoods = new ArrayList<>();
    private MoodAdapter adapter;
    private Date currentTime = Calendar.getInstance().getTime();

    // Tag for activity
    public static final String TAG = "MainActivity";

    private int tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvMoods);

        // Define layoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Attach adapter
        mRecyclerView.setAdapter(new MoodAdapter(mMoods));

        // Call methods
        updateUi();
        configureOnClickRecyclerView();
    }

    /**
     * Fill the RecyclerView with diffents moods (created through the mood class)
     */
    private void updateUi() {
        mMoods.add(new Mood(R.drawable.smiley_super_happy, R.color.banana_yellow, currentTime, "SuperHappy", 1));
        mMoods.add(new Mood(R.drawable.smiley_happy, R.color.light_sage, currentTime, "Happy", 2));
        mMoods.add(new Mood(R.drawable.smiley_normal, R.color.cornflower_blue_65, currentTime,"Normal", 3));
        mMoods.add(new Mood(R.drawable.smiley_disappointed, R.color.warm_grey, currentTime, "Disappointed", 4));
        mMoods.add(new Mood(R.drawable.smiley_sad, R.color.faded_red, currentTime, "Sad", 5));
    }

    /**
     * Check if a new day begins
     */
    private void isNewDay() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        if (hours*3600 + minutes*60 + seconds < 1800 ) {
            // It's a new day
        }
    }

    /**
     * Configure click on recyclerview
     */
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.item_mood)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Log.e(TAG, "Position : " + position);
                    }
                });
    }
}
