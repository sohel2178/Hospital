package com.baudiabatash.hospital.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baudiabatash.hospital.Model.Notes;
import com.baudiabatash.hospital.R;

import java.util.List;

/**
 * Created by Sohel on 1/12/2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {

    private Context context;
    private List<Notes> notesList;
    private LayoutInflater inflater;

    public NotesAdapter(Context context, List<Notes> notesList) {
        this.context = context;
        this.notesList = notesList;
        this.inflater =LayoutInflater.from(context);
    }

    @Override
    public NotesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_notes,parent,false);
        NotesHolder holder = new NotesHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NotesHolder holder, int position) {

        Notes notes = notesList.get(position);

        holder.tvNumber.setText(String.valueOf(position+1));
        holder.tvNotes.setText(notes.getNotes());
        holder.tvDate.setText(notes.getDate());

    }

    public void insertNotes(Notes notes){
        notesList.add(0,notes);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class NotesHolder extends RecyclerView.ViewHolder{
        TextView tvNumber,tvNotes,tvDate;

        public NotesHolder(View itemView) {
            super(itemView);
            tvNumber = (TextView) itemView.findViewById(R.id.number);
            tvNotes = (TextView) itemView.findViewById(R.id.notes);
            tvDate = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
