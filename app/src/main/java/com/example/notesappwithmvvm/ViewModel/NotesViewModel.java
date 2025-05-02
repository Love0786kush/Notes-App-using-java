package com.example.notesappwithmvvm.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesappwithmvvm.Model.Notes;
import com.example.notesappwithmvvm.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private NotesRepository repository;
    private LiveData<List<Notes>> allNotes;
    private LiveData<List<Notes>> highToLow;
    private LiveData<List<Notes>> lowToHigh;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        repository = new NotesRepository(application);

        allNotes = repository.getAllNotes();
        highToLow = repository.getHighToLow();
        lowToHigh = repository.getLowToHigh();
    }

    public void insertNote(Notes notes) {
        repository.insertNotes(notes);
    }

    public void deleteNote(int id) {
        repository.deleteNotes(id);
    }

    public void updateNote(Notes notes) {
        repository.updateNotes(notes);
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
