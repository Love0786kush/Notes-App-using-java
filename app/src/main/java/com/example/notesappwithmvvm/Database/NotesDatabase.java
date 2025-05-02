package com.example.notesappwithmvvm.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notesappwithmvvm.Dao.NotesDao;
import com.example.notesappwithmvvm.Model.Notes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Notes.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao(); // Correct DAO name

    private static volatile NotesDatabase INSTANCE; // Volatile to ensure thread safety

    // ExecutorService for background operations
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(1);

    public static NotesDatabase getDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (NotesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    NotesDatabase.class,
                                    "Notes_db"
                            )
                            .build(); // Removed allowMainThreadQueries() to avoid blocking the main thread
                }
            }
        }
        return INSTANCE;
    }
}
