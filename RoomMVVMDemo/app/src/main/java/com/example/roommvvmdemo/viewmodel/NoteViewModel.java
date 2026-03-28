package com.example.roommvvmdemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roommvvmdemo.data.NoteRepository;
import com.example.roommvvmdemo.data.local.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private final NoteRepository repo;
    private final LiveData<List<Note>> notesData;

    public NoteViewModel(@NonNull Application app) {
        super(app);
        repo = new NoteRepository(app);
        notesData = repo.getNotesStream();
    }

    public void addNote(Note item) {
        repo.add(item);
    }

    public void deleteNote(Note item) {
        repo.remove(item);
    }

    public void deleteEverything() {
        repo.clearAllData();
    }

    public LiveData<List<Note>> observeNotes() {
        return notesData;
    }
}