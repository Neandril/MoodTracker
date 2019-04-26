package com.neandril.moodtracker.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    public static final String PREFS_MOOD = "PREFS_MOOD";
    public static final String PREFS_MOOD_ID = "PREFS_MOOD_ID";
    public static final String PREFS_MOOD_COM = "PREFS_MOOD_COM";
    SharedPreferences sharedPreferences;

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

        // Check if sharedpreference contains a value
        sharedPreferences = getBaseContext().getSharedPreferences(PREFS_MOOD, MODE_PRIVATE);
        if (sharedPreferences.contains(PREFS_MOOD_ID) && sharedPreferences.contains(PREFS_MOOD_COM)){
            // If a mood was picked up, scroll to its position
            int pos = sharedPreferences.getInt(PREFS_MOOD_ID, 0);
            String com = sharedPreferences.getString(PREFS_MOOD_COM, null);
            Log.e(TAG, "sharedpreference : " + pos);
            Log.e(TAG, "sharedpreference : " + com);
            mRecyclerView.scrollToPosition(pos -1);

        } else {
            Log.e(TAG, "No sharedpreference yet");
        }

        // Call methods
        if (isNewDay()) {
            mRecyclerView.scrollToPosition(0);
        }
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
    private boolean isNewDay() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        int lastTimeStarted = settings.getInt("last_time_started", -1);
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_YEAR);

        if (today != lastTimeStarted) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("last_time_started", today);
            editor.apply();
            sharedPreferences
                    .edit()
                    .remove(PREFS_MOOD_ID).remove(PREFS_MOOD_COM)
                    .apply();
            Log.e(TAG, "New day !");
            return true;
        }

        return false;
    }

    /**
     * Configure click on recyclerview
     */
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.item_mood)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        int id = mMoods.get(position).getId();
                        String com = mMoods.get(position).getText();
                        sharedPreferences
                                .edit()
                                .putInt(PREFS_MOOD_ID, id)
                                .putString(PREFS_MOOD_COM, com)
                                .apply();
                        Log.e(TAG, "Position : " + mMoods.get(position).getId());
                    }
                });
    }
}
