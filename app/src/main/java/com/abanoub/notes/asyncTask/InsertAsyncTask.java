package com.abanoub.notes.asyncTask;

import android.os.AsyncTask;

import com.abanoub.notes.room.Note;
import com.abanoub.notes.room.NotesDao;

public class InsertAsyncTask extends AsyncTask<Note,Void,Void> {

    private NotesDao notesDao;

    public InsertAsyncTask(NotesDao notesDao){
        this.notesDao=notesDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        notesDao.Insert(notes[0]);
        return null;
    }
}
