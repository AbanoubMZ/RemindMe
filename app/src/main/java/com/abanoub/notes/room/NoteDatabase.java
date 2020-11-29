package com.abanoub.notes.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//Entity >> Table

@Database(entities = {Note.class},version = 1,exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;
    public abstract NotesDao notesDao();

    //Singleton
    public static synchronized NoteDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"note-database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    //in case i need to add static data to initialize the DB
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //new PopulateDataAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }

        @Override
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
        }
    };

    private static class PopulateDataAsyncTask extends AsyncTask<Void,Void, Void>
    {
        private NotesDao notesDao;

        PopulateDataAsyncTask(NoteDatabase db)
        {
            notesDao = db.notesDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            //Date date=java.util.Calendar.getInstance().getTime();
            //notesDao.Insert(new Note("book", "content 1", date.toString()));
            return null;
        }
    }
}
