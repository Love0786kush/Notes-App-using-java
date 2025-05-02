package com.example.notesappwithmvvm.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesappwithmvvm.Activity.UpdateNoteActivity;
import com.example.notesappwithmvvm.MainActivity;
import com.example.notesappwithmvvm.Model.Notes;
import com.example.notesappwithmvvm.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private MainActivity mainActivity;
    private List<Notes> notes;
    private List<Notes> allNoteItems;  // To hold the original unfiltered list

    // Constructor
    public NotesAdapter(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
        this.allNoteItems = new ArrayList<>(notes);  // Copy original list
    }

    // Method to filter notes list
    public void filterList(List<Notes> filteredList) {
        this.notes = filteredList;
        notifyDataSetChanged();  // Notify adapter about the data change
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.itemnotes, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Notes note = notes.get(position);

        // Priority check (assumes int type)
        int priority = note.getPriority();
        if (priority == 1) {
            holder.priorityView.setBackgroundResource(R.drawable.green);
        } else if (priority == 2) {
            holder.priorityView.setBackgroundResource(R.drawable.yellow);
        } else if (priority == 3) {
            holder.priorityView.setBackgroundResource(R.drawable.red);
        }

        holder.title.setText(note.getTitle());
        holder.subtitle.setText(note.getSubtitle());
        holder.dateText.setText(note.getDate());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mainActivity, UpdateNoteActivity.class);
            intent.putExtra("id", note.getId());
            intent.putExtra("Title", note.getTitle());
            intent.putExtra("Subtitle", note.getSubtitle());
            intent.putExtra("Priority", note.getPriority());
            intent.putExtra("Notes", note.getNotes());

            mainActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    // ViewHolder class to hold reference to the views
    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle, dateText;
        View priorityView;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
            dateText = itemView.findViewById(R.id.dateText);
            priorityView = itemView.findViewById(R.id.Priority);
        }
    }

    // Method to search notes by a query
    public void searchNotes(String query) {
        List<Notes> filteredList = new ArrayList<>();
        for (Notes note : allNoteItems) {
            // Check if title, subtitle, or notes contain the search query
            if (note.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    note.getSubtitle().toLowerCase().contains(query.toLowerCase()) ||
                    note.getNotes().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(note);
            }
        }
        filterList(filteredList);  // Call filterList() to update the adapter with filtered notes
    }
}
