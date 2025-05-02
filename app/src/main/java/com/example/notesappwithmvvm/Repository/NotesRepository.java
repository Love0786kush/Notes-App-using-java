package com.example.notesappwithmvvm.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.notesappwithmvvm.Dao.NotesDao;
import com.example.notesappwithmvvm.Database.NotesDatabase;
import com.example.notesappwithmvvm.Model.Notes;

import java.util.List;

public class NotesRepository {

    private NotesDao notesDao;
    private LiveData<List<Notes>> allNotes;
    private LiveData<List<Notes>> highToLow;
    private LiveData<List<Notes>> lowToHigh;

    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
        allNotes = notesDao.getAllNotes();
        highToLow = notesDao.getHighToLow(); // Match with DAO method name
        lowToHigh = notesDao.getLowToHigh(); // Match with DAO method name
    }

    public void insertNotes(Notes notes) {
        NotesDatabase.databaseWriteExecutor.execute(() -> notesDao.insertNotes(notes));
    }

    public void deleteNotes(int id) {
        NotesDatabase.databaseWriteExecutor.execute(() -> notesDao.deleteNotes(id));
    }

    public void updateNotes(Notes notes) {
        NotesDatabase.databaseWriteExecutor.execute(() -> notesDao.updateNotes(notes));
    }

    public LiveData<List<Notes>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Notes>> getHighToLow() {
        return highToLow;
    }

    public LiveData<List<Notes>> getLowToHigh() {
        return lowToHigh;
    }
}
