package com.abanoub.notes.asyncTask;

import android.os.AsyncTask;

import com.abanoub.notes.room.NotesDao;

public class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void> {
    private NotesDao notesDao;

    public DeleteAllAsyncTask(NotesDao notesDao) {
        this.notesDao = notesDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        notesDao.DeleteAllNotes();
        return null;
    }
}
