package com.example.notesappwithmvvm.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes_db")
public class Notes {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String subtitle;
    private String notes;
    public String date;
    private int priority; // Assuming priority is an integer

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getNotes() {
        return notes;
    }

    public int getPriority() {
        return priority;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }
}
