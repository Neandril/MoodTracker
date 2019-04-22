package com.neandril.moodtracker.Activities;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.neandril.moodtracker.Adapters.ViewPagerAdapter;
import com.neandril.moodtracker.Fragments.mood_disappointed_fragment;
import com.neandril.moodtracker.Fragments.mood_sad_fragment;
import com.neandril.moodtracker.Fragments.mood_superhappy_fragment;
import com.neandril.moodtracker.Fragments.mood_happy_fragment;
import com.neandril.moodtracker.Fragments.mood_normal_fragment;
import com.neandril.moodtracker.R;
import com.neandril.moodtracker.Views.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private VerticalViewPager mVerticalViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> list = new ArrayList<>();
        list.add(new mood_superhappy_fragment());
        list.add(new mood_happy_fragment());
        list.add(new mood_normal_fragment());
        list.add(new mood_disappointed_fragment());
        list.add(new mood_sad_fragment());

        mVerticalViewPager = findViewById(R.id.pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), list);

        mVerticalViewPager.setAdapter(mViewPagerAdapter);
    }
}
