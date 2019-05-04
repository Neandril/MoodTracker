package com.neandril.moodtracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.neandril.moodtracker.Adapters.HistoryAdapter;
import com.neandril.moodtracker.Adapters.MoodAdapter;
import com.neandril.moodtracker.Helpers.PrefHelper;
import com.neandril.moodtracker.Models.Mood;
import com.neandril.moodtracker.R;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    /**
     * In dev
     * @param savedInstanceState
     */

    private ArrayList<Mood> mMoods = new ArrayList<>();

    private RecyclerView recyclerView;
    private ImageButton button;
    private TextView textView;
    private PrefHelper prefHelper;

    LinearLayoutManager mLinearLayoutManager;

    public static final String TAG = "HistoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ArrayList<Mood> moodArrayList = prefHelper.getNewInstance(this).retrieveMoodList();

        Log.e(TAG, "Mood : " + moodArrayList.get(0).getId() + " - Date : " + moodArrayList.get(0).getDate() + " - Comment : " + moodArrayList.get(0).getComment());

        // Remove last item of the list (which is the current date)
        if (moodArrayList.size() > 0) {
            moodArrayList.remove(moodArrayList.size() -1);
        }

        // Display a toast message if there is no history yet
        Log.e(TAG, "Size : " + moodArrayList.size());
        if (moodArrayList.size() < 1) {
            Toast.makeText(this, "Historique inexistant. Revenez demain.", Toast.LENGTH_LONG).show();
        }

        Log.e(TAG, "ArrayList : " + moodArrayList.size());

        recyclerView = (RecyclerView) findViewById(R.id.rvHist);
        button = (ImageButton) findViewById(R.id.hist_commentBtn);
        textView = (TextView) findViewById(R.id.hist_textView);

        // Define layoutManager
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        // Attach adapter
        recyclerView.setAdapter(new HistoryAdapter(this, moodArrayList));
    }

}