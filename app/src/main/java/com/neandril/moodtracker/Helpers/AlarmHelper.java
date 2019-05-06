package com.neandril.moodtracker.Helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.neandril.moodtracker.Models.Mood;

import java.util.ArrayList;

/**
 * Creation of AlarmManager instead of JobScheduler,
 * because the app have to work on API lvl 19 and higher (Kit kat 4.4).
 * JobScheduler was implemented in API lvl 21.
 */
public class AlarmHelper extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
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
}
