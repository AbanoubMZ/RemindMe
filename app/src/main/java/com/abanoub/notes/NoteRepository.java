package com.abanoub.notes;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.abanoub.notes.asyncTask.DeleteAllAsyncTask;
import com.abanoub.notes.asyncTask.DeleteAsyncTask;
import com.abanoub.notes.asyncTask.InsertAsyncTask;
import com.abanoub.notes.asyncTask.UpdateAsyncTask;
import com.abanoub.notes.room.Note;
import com.abanoub.notes.room.NoteDatabase;
import com.abanoub.notes.room.NotesDao;

import java.util.List;

public class NoteRepository {

    private NotesDao notesDao;
    private LiveData<List<Note>> getAllNotes;


    public NoteRepository(Application app) {
       NoteDatabase noteDatabase = NoteDatabase.getInstance(app);
       notesDao = noteDatabase.notesDao();
       getAllNotes = notesDao.GetAllNotes();
    }

    public void Insert(Note note){
        new InsertAsyncTask(notesDao).execute(note);
    }

    public void Delete(Note note){
        new DeleteAsyncTask(notesDao).execute(note);
    }

    public void Update(Note note){
        new UpdateAsyncTask(notesDao).execute(note);
    }
    public LiveData<List<Note>> getGetAllNotes(){
        return  getAllNotes;
    }
    public void DeleteAllNotes(){
        new DeleteAllAsyncTask(notesDao).execute();
    }

}
