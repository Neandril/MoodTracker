package com.neandril.moodtracker.Helpers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.Calendar;

/**
 * Service which allows the history to be saved if application isn't running
 * since Oreo, we must create a notification channel if the service should run in foreground
 * https://developer.android.com/about/versions/oreo/android-8.0-changes
 */
public class AlarmService extends Service {

    public AlarmService() {
    }

    /**
     * onCreate method of the service
     * Since Android Oreo, service must run in foreground, with a notification channel
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
        Log.i("Service", "OnCreate");
    }

    /**
     * Start the command when service is triggered
     * @param intent - the intent
     * @param flags - flag number
     * @param startId - Id to start
     * @return - return START_STICKY : cause the service to restart automatically if killed or stopped
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i("Service", "onStartCommand");
        setAlarm(this);
        return Service.START_STICKY;
    }

    /**
     * onDestroy method of the service
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Service", "OnDestroy");
        Intent intent = new Intent(this, AlarmService.class);
        ContextCompat.startForegroundService(this, intent);
    }

    /**
     * Run the alarm for repeating every day at 23:59:59
     * @param context - context
     */
    private void setAlarm(Context context) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmHelper.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    /**
     * Mandatory method for binding the service
     * @param intent - the intent binded
     * @return - return type
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Start foreground service with notification
     * Mandatory since Oreo : https://developer.android.com/about/versions/oreo/android-8.0-changes
     * In addition, require a permission in the manifest
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground()
    {
        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("L'application est en cours d'exécution en arrière plan")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }
}
