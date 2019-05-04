package com.neandril.moodtracker.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neandril.moodtracker.Models.Mood;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class which helps to create preferences with Json
 * Used to send back the Json to the SaveMoodHelper class
 */

public class PrefHelper {

    private static PrefHelper newInstance;
    private static final String MOOD_LIST = "MOOD_LIST";
    private static final String PREFS_MOOD = "PREFS_MOOD";
    private static SharedPreferences sSharedPreferences;
    private ArrayList<Mood> moodArrayList;
    Gson gson;

    private PrefHelper(Context context) {
        sSharedPreferences = context.getSharedPreferences(PREFS_MOOD, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    /**
     * Initialize a new Instance of this class
     * @param context Context
     * @return return statement
     */
    public static PrefHelper getNewInstance(Context context) {
        if (newInstance == null) {
            newInstance = new PrefHelper(context);
        }
        return newInstance;
    }

    /**
     * Method used to save the mood list
     * @param moods An Array containing our moods infos
     */
    void saveMoodList(ArrayList<Mood> moods) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        String json = gson.toJson(moods);
        editor.putString(MOOD_LIST, json);
        editor.apply();
    }

    /**
     * Retrieve the moods list
     * @return an Array containing saved moods infos
     */
    public ArrayList<Mood> retrieveMoodList() {

        String json= sSharedPreferences.getString(MOOD_LIST, "[]");

        if (json == null || json.isEmpty()) {
            moodArrayList = new ArrayList<>();
        } else {
            moodArrayList = new ArrayList<>();
            Type type = new TypeToken<ArrayList<Mood>>() {}.getType();
            moodArrayList = gson.fromJson(json, type);
        }

        return moodArrayList;
    }
}
