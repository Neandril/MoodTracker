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
    private ImageButton commentBtn;
    private ImageButton histBtn;
    private MoodAdapter adapter;
    private Date currentTime = Calendar.getInstance().getTime();
    private String mComment;
    private Date mDate;
    private int positionId;

    LinearLayoutManager mLinearLayoutManager;

    // Tag for activity
    public static final String TAG = "MainActivity";

    public static final String PREFS_MOOD = "PREFS_MOOD";
    public static final String PREFS_MOOD_ID = "PREFS_MOOD_ID";
    public static final String PREFS_MOOD_COM = "PREFS_MOOD_COM";
    public static final String PREFS_MOOD_DATE = "PREFS_MOOD_DATE";
    public static final String PREFS_HIST_MOODS = "PREFS_HIST_MOODS";
    SharedPreferences sharedPreferences;
    SharedPreferences hSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // Call methods
        if (isNewDay()) {
            mRecyclerView.scrollToPosition(0);
        }

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
                    .remove(PREFS_MOOD_ID)
                    .apply();

            Log.e(TAG, "New day !");
            return true;
        }

        return false;
    }

    // Show an alertDialog allowing user to write a comment about his mood
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
                        positionId = mMoods.get(mLinearLayoutManager.findLastVisibleItemPosition()).getId();
                        mDate = mMoods.get(mLinearLayoutManager.findLastVisibleItemPosition()).getDate();
                        mComment = input.getText().toString();
                        mMoods.get(positionId).setComment(mComment);

                        saveHistoryMoods(mComment, mDate);
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

    // Run history activity when button is clicked
    private void configureHistBtn() {
        histBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    // If activity is paused, save current mood
    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences
                .edit()
                .putInt(PREFS_MOOD_ID, positionId)
                .apply();
    }

    // If activity is destroyed, save current mood
    @Override
    protected void onDestroy() {
        super.onDestroy();
        positionId = mMoods.get(mLinearLayoutManager.findLastVisibleItemPosition()).getId();
        sharedPreferences
                .edit()
                .putInt(PREFS_MOOD_ID, positionId)
                .apply();
    }

    public void saveHistoryMoods(String mComment, Date date) {
        positionId = mMoods.get(mLinearLayoutManager.findLastVisibleItemPosition()).getId();
        sharedPreferences
                .edit()
                .putInt(PREFS_MOOD_ID, positionId)
                .putString(PREFS_MOOD_COM, mComment)
                .putString(PREFS_MOOD_DATE, date.toString())
                .apply();
    }
}
