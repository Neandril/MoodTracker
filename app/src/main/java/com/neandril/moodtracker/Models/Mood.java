package com.neandril.moodtracker.Models;

import java.util.Date;

/**
 * Class used to create moods
 * @smiley - The mood picture displayed
 * @background - Background, according to the mood
 * @comment - Commentary
 * @date - Date
 * @id - The idendifier of the mood
 */
public class Mood {

    private int icon;
    private int background;
    private Date date;
    private String comment;
    private int id;

    // Constructor
    public Mood(int icon, int background, Date date, String comment, int id) {
        this.icon = icon;
        this.background = background;
        this.date = date;
        this.comment = comment;
        this.id = id;
    }

    // Getters and Setters

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

    public String getComment() {
        return comment;
    }

    public void setComment(String text) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
