package com.neandril.moodtracker.Helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
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

    public AlarmHelper() {

    }

    /**
     * When alarm is received, run the service intent
     * @param context context
     * @param intent the intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        intent = new Intent(context, AlarmService.class);
        ContextCompat.startForegroundService(context, intent); // ContextCompat : for compatibility before Oreo
    }
}
