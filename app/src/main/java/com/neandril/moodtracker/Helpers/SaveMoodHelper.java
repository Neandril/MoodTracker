package com.neandril.moodtracker.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neandril.moodtracker.Models.Mood;

import java.lang.reflect.Type;
import java.nio.channels.CancelledKeyException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Helper that helps to Save the moods inside history, according to the PrefHelper class
 */
public class SaveMoodHelper {

    private Context context;
    private SaveMoodHelper instance;
    private static SharedPreferences sPreferences;
    private static final String PREFS_MOOD = "PREFS_MOOD";
    private static final String MOOD_LIST = "MOOD_LIST";
    private DateHelper dateHelper;

    public SaveMoodHelper(Context context) {
        this.context = context;
    }

    /**
     * Save the current mood (called by MainActivity)
     * @param currentMood
     */
    public void saveCurrentMood(Mood currentMood) {

        dateHelper = new DateHelper();

        currentMood.setDate(dateHelper.getCurrentDate());

        // Initialize a new instance of the pref helper
        PrefHelper prefHelper = PrefHelper.getNewInstance(context);
        ArrayList<Mood> moodArrayList = prefHelper.retrieveMoodList();

        if (moodArrayList == null) {
            moodArrayList = new ArrayList<>();
        }

        // Remove the last item if the list is not null, and if it's the same date
        if (moodArrayList.size() > 0 && (moodArrayList.get(moodArrayList.size()-1).getDate()).equals(dateHelper.getCurrentDate())) {
            moodArrayList.remove(moodArrayList.size() -1);
        }

        // Add current mood infos to the list
        moodArrayList.add(currentMood);

        // If the array contains more than 8 entries, delete the older one (reverse computing : 0 is the older)
        if (moodArrayList.size() > 8) {
            moodArrayList.remove(0);
            Log.e("SaveMoodHelper", " Remove last item, size : " + moodArrayList.size());
        }

        // Finally save the array
        prefHelper.saveMoodList(moodArrayList);
    }
}
