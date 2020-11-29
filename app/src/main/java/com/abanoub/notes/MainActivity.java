package com.abanoub.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.abanoub.notes.adapter.NoteRvAdapter;
import com.abanoub.notes.room.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private RecyclerView notesRecyclerView;
    private NoteRvAdapter noteRvAdapter;
    private ImageView floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //floating button
        floatingActionButton=findViewById(R.id.add_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent,1);
            }
        });

        //RecyclerView
        notesRecyclerView=findViewById(R.id.notes_RV);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesRecyclerView.setHasFixedSize(true);

        //Connect RecyclerView with Adapter
        noteRvAdapter = new NoteRvAdapter();
        notesRecyclerView.setAdapter(noteRvAdapter);

        //noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.GetAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //Update Ui
                //RecyclerView
                //Toast.makeText(MainActivity.this, notes.size()+"", Toast.LENGTH_SHORT).show();
                noteRvAdapter.setNotes(notes);
            }
        });
        noteRvAdapter.OnItemClickListener(new NoteRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra(AddNoteActivity.EXTRA_ID,note.getId());
                intent.putExtra(AddNoteActivity.EXTRA_TITLE,note.getNoteTitle());
                intent.putExtra(AddNoteActivity.EXTRA_CONTENT,note.getNoteContent());
                startActivity(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //Delete Note from List
                noteViewModel.Delete(noteRvAdapter.getNote(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(notesRecyclerView);
    }
}