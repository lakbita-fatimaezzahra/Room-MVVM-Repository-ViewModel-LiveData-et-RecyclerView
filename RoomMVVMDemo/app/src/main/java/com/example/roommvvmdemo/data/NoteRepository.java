package com.example.roommvvmdemo.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roommvvmdemo.data.local.Note;
import com.example.roommvvmdemo.data.local.NoteDao;
import com.example.roommvvmdemo.data.local.NoteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {

    private final NoteDao dao;
    private final LiveData<List<Note>> notesLiveData;
    private final ExecutorService backgroundExecutor;

    public NoteRepository(Application app) {
        NoteDatabase db = NoteDatabase.getInstance(app);
        dao = db.getNoteDao();
        notesLiveData = dao.fetchAllNotes();
        backgroundExecutor = Executors.newSingleThreadExecutor();
    }

    public void add(Note item) {
        backgroundExecutor.execute(() -> dao.addNote(item));
    }

    public void remove(Note item) {
        backgroundExecutor.execute(() -> dao.removeNote(item));
    }

    public void clearAllData() {
        backgroundExecutor.execute(dao::clearAll);
    }

    public LiveData<List<Note>> getNotesStream() {
        return notesLiveData;
    }
}