package com.neandril.moodtracker.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.neandril.moodtracker.Adapters.MoodAdapter;
import com.neandril.moodtracker.Helpers.PrefHelper;
import com.neandril.moodtracker.Helpers.SaveMoodHelper;
import com.neandril.moodtracker.Models.Mood;
import com.neandril.moodtracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Entry point of the app
 */

public class MainActivity extends AppCompatActivity {

    // Declarations
    private RecyclerView mRecyclerView;
    private ArrayList<Mood> mMoods = new ArrayList<>();
    private ImageButton commentBtn;
    private ImageButton histBtn;
    private Date currentTime = Calendar.getInstance().getTime();
    private String mComment;
    private int positionId;
    SaveMoodHelper saveMoodHelper;

    LinearLayoutManager mLinearLayoutManager;

    // Tag for activity's log
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveMoodHelper = new SaveMoodHelper(this);

        /**
         // Check if sharedpreference contains a value
         sharedPreferences = getBaseContext().getSharedPreferences(PREFS_MOOD, MODE_PRIVATE);

         if (sharedPreferences.contains(PREFS_MOOD_ID) && sharedPreferences.contains(PREFS_MOOD_COM)){
            // If a mood was picked up, scroll to its position
            int pos = sharedPreferences.getInt(PREFS_MOOD_ID, 0);
            String com = sharedPreferences.getString(PREFS_MOOD_COM, "");
            String date = sharedPreferences.getString(PREFS_MOOD_DATE, "");
            mRecyclerView.scrollToPosition(pos);

            Log.e(TAG, "Mood : " + pos + " - Commentaire : " + com + " - Date : " + date);

        } else {
            Log.e(TAG, "No sharedpreference yet");
        }
         **/

        configureRecyclerView();
        updateUi();
        configureCommentBtn();
        configureHistBtn();
    }

    /**
     * Fill the RecyclerView with diffents moods (created through the mood class)
     */
    private void updateUi() {
        mMoods.add(new Mood(R.drawable.smiley_super_happy, R.color.banana_yellow, currentTime, "", 0));
        mMoods.add(new Mood(R.drawable.smiley_happy, R.color.light_sage, currentTime, "", 1));
        mMoods.add(new Mood(R.drawable.smiley_normal, R.color.cornflower_blue_65, currentTime,"", 2));
        mMoods.add(new Mood(R.drawable.smiley_disappointed, R.color.warm_grey, currentTime, "", 3));
        mMoods.add(new Mood(R.drawable.smiley_sad, R.color.faded_red, currentTime, "", 4));
    }

    /**
     * Method configuring the RecyclerView
     */
    private void configureRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvMoods);
        commentBtn = (ImageButton) findViewById(R.id.commentBtn);
        histBtn = (ImageButton) findViewById(R.id.historyBtn);

        // Define layoutManager
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Attach adapter
        mRecyclerView.setAdapter(new MoodAdapter(mMoods));

        // Attach a snapHelper (android.v7)
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        ArrayList<Mood> moodArrayList = PrefHelper.getNewInstance(getApplicationContext()).retrieveMoodList();
        if (moodArrayList == null) {
            moodArrayList = new ArrayList<>();
            mRecyclerView.scrollToPosition(0);
        } else {
            positionId = moodArrayList.get(moodArrayList.size() -1).getId();
            mRecyclerView.scrollToPosition(positionId);
        }

        // Call methods
        if (isNewDay()) {
            mRecyclerView.scrollToPosition(0);
        }
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

            Log.e(TAG, "New day !");
            return true;
        }

        return false;
    }

    /**
     * Add a comment button
     */
    private void configureCommentBtn() {
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.comment);
                final EditText input = new EditText(getApplicationContext());
                builder.setView(input);

                builder.setPositiveButton(R.string.positiveBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PrefHelper.getNewInstance(getApplicationContext());
                        mComment = input.getText().toString();
                        mMoods.get(positionId).setComment(mComment);
                        saveMoodHelper.saveCurrentMood(mMoods.get(positionId));
                    }
                });
                builder.setNegativeButton(R.string.negativeBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    /**
     * Launch history activity
     * coming soon
     */
    private void configureHistBtn() {
        histBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                // startActivity(intent);
                ArrayList<Mood> moodArrayList = PrefHelper.getNewInstance(getApplicationContext()).retrieveMoodList();
                Log.e("SaveMoodHelper", "Array : " + moodArrayList);
            }
        });
    }

    /**
     * Life cycle methods
     */
    // When paused
    @Override
    protected void onPause() {
        super.onPause();
        positionId = mMoods.get(mLinearLayoutManager.findLastVisibleItemPosition()).getId();
        Log.e(TAG, "MOOD ID : " + positionId + " - COMMENT : " + mComment);
        saveMoodHelper.saveCurrentMood(mMoods.get(positionId));
    }

    // When destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     *
     */
}
