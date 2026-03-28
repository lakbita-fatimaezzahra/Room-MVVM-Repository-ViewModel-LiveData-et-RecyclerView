package com.example.roommvvmdemo.data.local;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao getNoteDao();

    private static volatile NoteDatabase dbInstance;

    public static NoteDatabase getInstance(Context context) {
        if (dbInstance == null) {
            synchronized (NoteDatabase.class) {
                if (dbInstance == null) {
                    dbInstance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    NoteDatabase.class,
                                    "notes_database"  // Correction : ajout du nom de la base
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return dbInstance;
    }
}