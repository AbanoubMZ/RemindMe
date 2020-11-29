package com.abanoub.notes.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao        
public interface NotesDao {
    @Insert
    void Insert(Note note);

    @Update
    void Update(Note note);

    @Delete
    void Delete(Note note);

    @Query("DELETE From notesTable")
    void DeleteAllNotes();

    @Query("SELECT * From notesTable")
    LiveData<List<Note>> GetAllNotes();
}
