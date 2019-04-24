package com.neandril.moodtracker.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neandril.moodtracker.Models.Mood;
import com.neandril.moodtracker.R;
import com.neandril.moodtracker.Views.RecyclerViewHolder;

import java.util.List;

public class MoodAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    List<Mood> list;

    public static final String TAG = "MainActivity";

    //ajouter un constructeur prenant en entrée une liste
    public MoodAdapter(List<Mood> list) {
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mood,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        Mood myObject = list.get(position);
        viewHolder.bind(myObject);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Clicked !");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
