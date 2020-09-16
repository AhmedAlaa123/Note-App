package com.example.noteproject.databse;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);
    @Update
    void update(Note note);
    @Delete
    void delete(Note note);
    @Query("DELETE FROM note_table")
    void deleteAll();


    //using live data if there is updata in the table the get this update
    @Query("select * from note_table order by pariorty desc")
    LiveData<List<Note>> getAllNotes();
}
