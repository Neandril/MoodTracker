package com.neandril.moodtracker.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.neandril.moodtracker.adapters.MoodAdapter;
import com.neandril.moodtracker.helpers.AlarmHelper;
import com.neandril.moodtracker.helpers.DateHelper;
import com.neandril.moodtracker.helpers.PrefHelper;
import com.neandril.moodtracker.models.Mood;
import com.neandril.moodtracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Entry point of the app
 */

public class MainActivity extends AppCompatActivity {

    // Declarations
    private final ArrayList<Mood> mMoods = new ArrayList<>();
    private final HashMap<Object, String> shareInfos = new HashMap<>();
    private ImageButton commentBtn;
    private ImageButton histBtn;
    private ImageButton shareBtn;
    private String mComment;
    private int positionId;
    private String path;
    private Bitmap mBitmap;
    private String shareText;
    private PrefHelper prefHelper;
    private final String currentDate = DateHelper.getCurrentDate();
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("MainActivity", "onCreate");

        prefHelper = PrefHelper.getNewInstance(this);

        // Other methods are in onResume
        updateUi();
        callAlarm(this);
    }

    /**
     * Fill the RecyclerView with diffents moods (created through the mood class)
     */
    private void updateUi() {
        mMoods.clear();
        mMoods.add(new Mood(R.drawable.smiley_super_happy, R.color.banana_yellow, currentDate, "", 0));
        mMoods.add(new Mood(R.drawable.smiley_happy, R.color.light_sage, currentDate, "", 1));
        mMoods.add(new Mood(R.drawable.smiley_normal, R.color.cornflower_blue_65, currentDate,"", 2));
        mMoods.add(new Mood(R.drawable.smiley_disappointed, R.color.warm_grey, currentDate, "", 3));
        mMoods.add(new Mood(R.drawable.smiley_sad, R.color.faded_red, currentDate, "", 4));
    }

    /**
     * Method configuring the RecyclerView
     */
    private void configureRecyclerView() {
        // Initialisation
        RecyclerView mRecyclerView = findViewById(R.id.rvMoods);
        commentBtn = findViewById(R.id.commentBtn);
        histBtn = findViewById(R.id.historyBtn);
        shareBtn = findViewById(R.id.shareBtn);

        // Define layoutManager
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Attach adapter
        mRecyclerView.setAdapter(new MoodAdapter(mMoods));

        // Attach a snapHelper (android.v7)
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        // Scroll the recycler view according to the last item selected today
        ArrayList<Mood> moodArrayList = PrefHelper.getNewInstance(this).retrieveMoodList();
        if ((moodArrayList.size() > 0) && (currentDate.equals(moodArrayList.get(moodArrayList.size() -1).getDate()))) {
            mRecyclerView.scrollToPosition(moodArrayList.get(moodArrayList.size() -1).getId());
        } else {
            mRecyclerView.scrollToPosition(0);
        }
    }

    /**
     * Add a comment button
     */
    private void configureCommentBtn() {
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.comment);
                final EditText input = new EditText(getApplicationContext());
                builder.setView(input);

                builder.setPositiveButton(R.string.positiveBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PrefHelper.getNewInstance(MainActivity.this);
                        mComment = input.getText().toString();
                        mMoods.get(positionId).setComment(mComment);
                        Mood currentMood = mMoods.get(positionId);
                        prefHelper.saveCurrentMood(currentMood);
                    }
                });
                builder.setNegativeButton(R.string.negativeBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    /**
     * Launch history activity
     */
    private void configureHistBtn() {
        histBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Share button : display an option picker for sharing current mood
     * (by mail, sms, social networks, etc.)
     */
    private void configureShareBtn() {
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve share infos
                getShareInfos().get(0);

                // Ignore URI Exposure
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                path = getExternalCacheDir() + "/moodImage.png";

                java.io.OutputStream out;
                java.io.File file=new java.io.File(path);
                try {
                    out = new java.io.FileOutputStream(file);
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                path = file.getPath();
                Uri imageUri = Uri.parse("file://" + path);

                Intent share = new Intent(Intent.ACTION_SEND);
                share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.strShareSubject, shareText));
                share.putExtra(Intent.EXTRA_STREAM,imageUri);
                share.putExtra(Intent.EXTRA_TEXT, getString(R.string.strShareExtraText));

                startActivity(Intent.createChooser(share, getString(R.string.strShareChooser)));
            }
        });
    }

    /**
     * Method returning a Hashmap containing share infos
     * @return - HashMap of objects (the bitmap), and a string (the share comment)
     */
    private HashMap<Object, String> getShareInfos() {
        positionId = mMoods.get(mLinearLayoutManager.findLastVisibleItemPosition()).getId();
        switch (positionId) {
            case 0:
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.smiley_super_happy);
                shareText = getResources().getString(R.string.imsuperhappy);
                break;
            case 1:
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.smiley_happy);
                shareText = getResources().getString(R.string.imhappy);
                break;
            case 2:
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.smiley_normal);
                shareText = getResources().getString(R.string.imnormal);
                break;
            case 3:
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.smiley_disappointed);
                shareText = getResources().getString(R.string.imdisappointed);
                break;
            case 4:
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.smiley_sad);
                shareText = getResources().getString(R.string.imsad);
                break;
            default:
                break;
        }
        shareInfos.put(mBitmap, shareText);
        return shareInfos;
    }

    /**
     * Method calling the alarm, everyday at 23:59:59.
     */
    private void callAlarm(Context context) {
        AlarmManager alarmManager;
        PendingIntent pendingIntent;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.add(Calendar.DATE, 1);

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmHelper.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    /*
      Life cycle methods
     */

    /**
     * Configure the onPause method to save mood if the app is just paused
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause");
        positionId = mMoods.get(mLinearLayoutManager.findLastVisibleItemPosition()).getId();
        mMoods.get(positionId).setComment(mComment);

        prefHelper.saveCurrentMood(mMoods.get(positionId));
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "onDestroy");
        super.onDestroy();
    }

    /**
     * Configure the onResume method
     * Refresh the app itself if it is not closed
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
        setContentView(R.layout.activity_main);

        configureRecyclerView();
        configureCommentBtn();
        configureHistBtn();
        configureShareBtn();
    }
}
