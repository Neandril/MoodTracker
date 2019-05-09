package com.neandril.moodtracker.Helpers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.neandril.moodtracker.Models.Mood;

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
        PrefHelper prefHelper = PrefHelper.getNewInstance(context);
        ArrayList<Mood> moodArrayList = prefHelper.retrieveMoodList();

        if (moodArrayList == null || moodArrayList.isEmpty()) {
            moodArrayList = new ArrayList<>();
        }

        // Remove the last item if the list is not null, and if it's the same date
        DateHelper dateHelper = new DateHelper();
        if (moodArrayList.size() > 0 && (moodArrayList.get(moodArrayList.size()-1).getDate()).equals(dateHelper.getCurrentDate())) {
            moodArrayList.remove(moodArrayList.size() -1);
        }

        if (moodArrayList.size() > 7 ) {
            moodArrayList.remove(0);
        }

        prefHelper.saveMoodList(moodArrayList);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        // In the case of this app, no need to return anything.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
