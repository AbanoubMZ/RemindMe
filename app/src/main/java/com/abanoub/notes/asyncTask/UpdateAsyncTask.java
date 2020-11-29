package com.abanoub.notes.asyncTask;

import android.os.AsyncTask;

import com.abanoub.notes.room.Note;
import com.abanoub.notes.room.NotesDao;

public class UpdateAsyncTask extends AsyncTask<Note,Void,Void> {
    private NotesDao notesDao;


    public UpdateAsyncTask(NotesDao notesDao){
        this.notesDao = notesDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        notesDao.Update(notes[0]);
        return null;
    }
}
