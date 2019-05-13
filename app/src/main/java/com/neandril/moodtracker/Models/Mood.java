package com.neandril.moodtracker.Models;

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
    private String date;
    private String comment;
    private int id;

    // Constructor
    public Mood(int icon, int background, String date, String comment, int id) {
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

    public int getBackground() {
        return background;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

}
