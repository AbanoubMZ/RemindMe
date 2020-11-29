package com.abanoub.notes.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.abanoub.notes.room.Note;
import com.abanoub.notes.R;

import java.util.ArrayList;
import java.util.List;

public class NoteRvAdapter extends RecyclerView.Adapter<NoteRvAdapter.NoteViewHolder> {
    private List<Note>notesList=new ArrayList<>();
    private  OnItemClickListener mListener;

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item,parent,false);
        return new NoteViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote=notesList.get(position);
        if(currentNote.getNoteImportance().equals("High")){
            holder.constraintLayout.setBackgroundResource(R.color.high);
        }else if(currentNote.getNoteImportance().equals("Medium")){
            holder.constraintLayout.setBackgroundResource(R.color.medium);
        }else {
            holder.constraintLayout.setBackgroundResource(R.color.low);
        }
        holder.noteTV.setText(currentNote.getNoteTitle());
        holder.contentTV.setText(currentNote.getNoteContent());
        holder.timeTV.setText(currentNote.getNoteTime());
    }

    public void setNotes(List<Note>notes){
        notesList=notes;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{
        public TextView noteTV;
        public TextView contentTV;
        public TextView timeTV;
        public ConstraintLayout constraintLayout;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTV=itemView.findViewById(R.id.note_tv);
            contentTV=itemView.findViewById(R.id.content_tv);
            timeTV=itemView.findViewById(R.id.time_tv);
            constraintLayout =itemView.findViewById(R.id.note_item_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(mListener != null && position != RecyclerView.NO_POSITION){
                        mListener.onItemClick(notesList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(Note note);
    }
    public void OnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public Note getNote(int position){
        return notesList.get(position);
    }

}
