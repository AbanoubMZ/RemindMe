package com.abanoub.notes.room;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notesTable")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String noteTime;
    private String noteTitle;
    private String noteContent;
    private String noteImportance;

    public Note(String noteTitle, String noteContent, String noteTime,String noteImportance) {
        this.noteTime = noteTime;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteImportance = noteImportance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteImportance() {
        return noteImportance;
    }

    public void setNoteImportance(String noteImportance) {
        this.noteImportance = noteImportance;
    }
    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
