package com.neandril.moodtracker.Models;

import com.neandril.moodtracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Class used to create moods
 * @smiley
 * @background
 * @comment
 * @date
 * @id
 */
public class Mood {

    private int icon;
    private int background;
    private Date date;
    private String text;
    private int id;

    public Mood(int icon, int background, Date date, String text, int id) {
        this.icon = icon;
        this.background = background;
        this.date = date;
        this.text = text;
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
