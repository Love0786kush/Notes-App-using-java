package com.example.notesappwithmvvm.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.notesappwithmvvm.Model.Notes;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    void insertNotes(Notes notes);

    @Query("DELETE FROM Notes_db WHERE id = :id")
    void deleteNotes(int id);

    @Update
    void updateNotes(Notes notes);

    @Query("SELECT * FROM Notes_db")
    LiveData<List<Notes>> getAllNotes();

    // Get sorted notes by priority descending (High to Low)
    @Query("SELECT * FROM Notes_db ORDER BY priority DESC")
    LiveData<List<Notes>> getHighToLow();  // Change to match your repo/viewmodel

    // Get sorted notes by priority ascending (Low to High)
    @Query("SELECT * FROM Notes_db ORDER BY priority ASC")
    LiveData<List<Notes>> getLowToHigh();  // Change to match your repo/viewmodel
}
