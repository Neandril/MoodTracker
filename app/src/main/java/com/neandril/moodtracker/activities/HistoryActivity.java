package com.neandril.moodtracker.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.neandril.moodtracker.adapters.HistoryAdapter;
import com.neandril.moodtracker.helpers.PrefHelper;
import com.neandril.moodtracker.models.Mood;
import com.neandril.moodtracker.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * History activity
 * The history is contained inside a recyclerview, like mainActivity
 */
public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ArrayList<Mood> moodArrayList = PrefHelper.getNewInstance(this).retrieveMoodList();

        // Remove last item of the list (which is the current date), and then reverse the whole list to display it on the right order
        if (moodArrayList.size() > 0) {
            moodArrayList.remove(moodArrayList.size() -1);
            Collections.reverse(moodArrayList);
        }

        // Display a toast message if there is no history yet
        if (moodArrayList.size() < 1) {
            Toast.makeText(this, "Historique inexistant. Revenez demain.", Toast.LENGTH_LONG).show();
        }

        RecyclerView recyclerView = findViewById(R.id.rvHist);

        // Define layoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Attach adapter
        recyclerView.setAdapter(new HistoryAdapter(this, moodArrayList));
    }

}
