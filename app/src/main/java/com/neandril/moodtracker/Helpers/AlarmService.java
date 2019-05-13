package com.neandril.moodtracker.Helpers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.neandril.moodtracker.Models.Mood;
import com.neandril.moodtracker.R;

import java.util.ArrayList;

/**
 * Create a service allowing the alarm to work if app is closed
 */
public class AlarmService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        CallTask(this);
        return Service.START_STICKY;
    }

    /**
     * This method save default mood if no mood were picked today
     * @param context context
     */
    private void CallTask(Context context){
        DateHelper dateHelper = new DateHelper();
        PrefHelper prefHelper = PrefHelper.getNewInstance(context);
        ArrayList<Mood> moodArrayList = prefHelper.retrieveMoodList();

        // If the array is null or empty, create a new one
        if (moodArrayList == null || moodArrayList.isEmpty()) {
            moodArrayList = new ArrayList<>();
        }

        // If the array isn't emptyn and the date not equals to current date, create a new default mood and add it to the array
        if (moodArrayList.size() > 0 && (!(moodArrayList.get(moodArrayList.size()-1).getDate()).equals(dateHelper.getCurrentDate()))) {
            Mood defaultMood = new Mood(R.drawable.smiley_super_happy, R.color.banana_yellow, dateHelper.getCurrentDate(), "", 0);
            moodArrayList.add(defaultMood);
        }

        // If the array contains more than 7 entries, delete last one
        if (moodArrayList.size() > 7 ) {
            moodArrayList.remove(0);
        }

        // Save the mood (i.e. the default one)
        prefHelper.saveMoodList(moodArrayList);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        // In the case of this app, no need to return anything.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
