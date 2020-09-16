package com.example.noteproject.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.noteproject.databse.Note;
import com.example.noteproject.databse.NoteDao;
import com.example.noteproject.databse.NoteDataBase;

import java.util.List;

//this class used to dell with the room database
public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDataBase noteDataBase = NoteDataBase.getInstance(application);
        noteDao = noteDataBase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    //this method for inserting note in the database
    public void insert(Note note) {
        new InsertAsyncTask(this.noteDao).execute(note);
    }
//this method for updating note in the database
    public void update(Note note) {
        new UpdatetAsyncTask(this.noteDao).execute(note);
    }

//this method for deleting note in the database
    public void delete(Note note) {
        new DeleteAsyncTask(this.noteDao).execute(note);
    }

//this method for delete all notes in the database
    public void deleteAllNotes() {
        new DeleteAlltAsyncTask(this.noteDao).execute();
    }
    public LiveData<List<Note>> getAllNotes()
    {
        return this.allNotes;
    }
//this class to insert note in the background
    public static class InsertAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public InsertAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
//this class to update note in the background

    public static class UpdatetAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public UpdatetAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
//this class to delete note in the background

    public static class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public DeleteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
//this class to delete all notes in the background

    public static class DeleteAlltAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        public DeleteAlltAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }

}
