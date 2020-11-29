package com.abanoub.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abanoub.notes.room.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<Note>> notesList;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository= new NoteRepository(application);
        notesList= noteRepository.getGetAllNotes();
    }

    public void Insert (Note note){
        noteRepository.Insert(note);
    }

    public void Delete (Note note){
        noteRepository.Delete(note);
    }

    public void Update (Note note){
        noteRepository.Update(note);
    }

    public void DeleteAllNotes(){
        noteRepository.DeleteAllNotes();
    }
    public LiveData<List<Note>> GetAllNotes(){
        return notesList;
    }
}
