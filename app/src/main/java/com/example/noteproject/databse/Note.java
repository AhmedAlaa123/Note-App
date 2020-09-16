package com.example.noteproject.databse;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private  int id;
    private  int pariorty;
    private String title;
    private String description;

    public Note(int pariorty, String title, String description) {
        this.pariorty = pariorty;
        this.title = title;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getPariorty() {
        return pariorty;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
