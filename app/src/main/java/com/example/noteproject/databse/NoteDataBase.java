package com.example.noteproject.databse;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class},
        version = 1)
public abstract class NoteDataBase extends RoomDatabase {
    private static NoteDataBase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDataBase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    //this callback object to impelement oncreate method in the database
    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {

            super.onCreate(db);
            new PobulateDbAsyncTask(instance).execute();
        }
    };

    //this for adding some note to be shown on create database  in recycler view
    private static class PobulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        public PobulateDbAsyncTask(NoteDataBase db) {
            this.noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.noteDao.insert(new Note(1, "title 1", "Description 1"));
            this.noteDao.insert(new Note(2, "title 2", "Description 2"));
            this.noteDao.insert(new Note(3, "title 3", "Description 3"));

            return null;
        }
    }
}
