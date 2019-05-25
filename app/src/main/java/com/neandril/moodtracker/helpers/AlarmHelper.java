package com.neandril.moodtracker.helpers;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.neandril.moodtracker.activities.MainActivity;
import com.neandril.moodtracker.models.Mood;
import com.neandril.moodtracker.R;

import java.util.ArrayList;

/**
 * Creation of AlarmManager instead of JobScheduler,
 * because the app have to work on API lvl 19 and higher (Kit kat 4.4).
 * JobScheduler was implemented in API lvl 21.
 */
public class AlarmHelper extends BroadcastReceiver {

    /**
     * When alarm is received, save a default mood or current mood (depending...)
     * @param context context
     * @param intent the intent
     */
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("Alarm", "Alarm Triggered");

        String currentDate = DateHelper.getCurrentDate();

        PrefHelper prefHelper = PrefHelper.getNewInstance(context);
        ArrayList<Mood> moodArrayList = prefHelper.retrieveMoodList();

        // If the array is null or empty, create a new one
        if (moodArrayList == null || moodArrayList.isEmpty()) {
            moodArrayList = new ArrayList<>();
        }

        // If the array isn't empty and the date not equals to current date, create a new default mood and add it to the array
        if (moodArrayList.size() > 0 && (!(moodArrayList.get(moodArrayList.size()-1).getDate()).equals(currentDate))) {
            Mood defaultMood = new Mood(R.drawable.smiley_super_happy, R.color.banana_yellow, currentDate, "", 0);
            moodArrayList.add(defaultMood);
        }

        // If the array contains more than 8 entries, delete last one
        if (moodArrayList.size() > 8 ) {
            moodArrayList.remove(0);
        }

        // Save the mood (i.e. the default one)
        prefHelper.saveMoodList(moodArrayList);
    }
}
