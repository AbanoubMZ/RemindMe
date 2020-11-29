package com.abanoub.notes.asyncTask;

import android.os.AsyncTask;

import com.abanoub.notes.room.Note;
import com.abanoub.notes.room.NotesDao;

public class DeleteAsyncTask extends AsyncTask<Note,Void,Void> {

    private NotesDao notesDao;

    public DeleteAsyncTask(NotesDao notesDao) {
        this.notesDao = notesDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        notesDao.Delete(notes[0]);
        return null;
    }
}
