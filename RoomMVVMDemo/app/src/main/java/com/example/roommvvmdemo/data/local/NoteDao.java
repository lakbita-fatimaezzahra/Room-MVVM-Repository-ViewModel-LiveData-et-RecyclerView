package com.example.roommvvmdemo.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void addNote(Note item);

    @Delete
    void removeNote(Note item);

    @Query("DELETE FROM notes")
    void clearAll();

    @Query("SELECT * FROM notes ORDER BY noteId DESC")
    LiveData<List<Note>> fetchAllNotes();  // Correction : nom cohérent
}