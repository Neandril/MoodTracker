package com.neandril.moodtracker.Activities;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.neandril.moodtracker.Adapters.MoodAdapter;
import com.neandril.moodtracker.Models.Mood;
import com.neandril.moodtracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Mood> mMoods = new ArrayList<>();

    private Date currentTime = Calendar.getInstance().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateUi();

        mRecyclerView = (RecyclerView) findViewById(R.id.rvMoods);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MoodAdapter(mMoods));
    }

    private void updateUi() {
        mMoods.add(new Mood(R.drawable.smiley_super_happy, R.color.banana_yellow, currentTime, "SuperHappy", 1));
        mMoods.add(new Mood(R.drawable.smiley_happy, R.color.light_sage, currentTime, "Happy", 2));
        mMoods.add(new Mood(R.drawable.smiley_normal, R.color.cornflower_blue_65, currentTime,"Normal", 3));
        mMoods.add(new Mood(R.drawable.smiley_disappointed, R.color.warm_grey, currentTime, "Disappointed", 4));
        mMoods.add(new Mood(R.drawable.smiley_sad, R.color.faded_red, currentTime, "Sad", 5));
    }

    private void isNewDay() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        if (hours*3600 + minutes*60 + seconds < 1800 ) {
            // It's a new day
        }
    }
}
