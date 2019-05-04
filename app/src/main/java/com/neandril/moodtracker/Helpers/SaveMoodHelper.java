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

    public SaveMoodHelper(Context context) {
        this.context = context;
    }

    /**
     * Get the current date
     * @return current date
     */
    private String getCurrentDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        String strDate = format.format(today);

        return strDate;
        /**
        Date date;
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/YYYY", Locale.getDefault());
        date = Calendar.getInstance().getTime();
        date = new Date(outputFormatter.format(date));
        return date;
         **/
    }

    /**
     * Save the current mood (called by MainActivity)
     * @param currentMood
     */
    public void saveCurrentMood(Mood currentMood) {

        currentMood.setDate(getCurrentDate());
        Log.e("SaveMoodHelper", getCurrentDate());

        // Initialize a new instance of the pref helper
        PrefHelper prefHelper = PrefHelper.getNewInstance(context);
        ArrayList<Mood> moodArrayList = prefHelper.retrieveMoodList();

        // Remove the last item if the list is not null, and if it's the same date
        if (moodArrayList.size() > 0 && (moodArrayList.get(moodArrayList.size()-1).getDate()).equals(getCurrentDate())) {
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

        Log.e("SaveMoodHelper", "Saved ! " + moodArrayList.get(0).getComment());

        // Below, older code, just a remember; will be deleted
        /**
        SharedPreferences preferences = context.getSharedPreferences(PREFS_MOOD, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        ArrayList<Mood> moods = new ArrayList<>();
        Gson gson = new Gson();

        String json = gson.toJson(moods);
        editor.putString(MOOD_LIST, json);
        editor.apply();

        Log.e("SaveMoodHelper", "Saved ! " + json);
         **/
    }

    /**
     * Load Preferences
     * @param context context
     * @return array of moods
     */
    public ArrayList<Mood> loadPreferences(Context context) {
        Gson gson = new Gson();

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_MOOD, 0);
        String json = sharedPreferences.getString(MOOD_LIST, "");
        Type type = new TypeToken<ArrayList<Mood>>() {}.getType();

        ArrayList<Mood> moods = gson.fromJson(json, type);

        if (moods == null) {
            moods = new ArrayList<>();
        }

        return moods;
    }
}
