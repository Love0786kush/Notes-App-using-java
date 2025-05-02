package com.example.notesappwithmvvm;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesappwithmvvm.Activity.InsertNoteActivity;
import com.example.notesappwithmvvm.Adapter.NotesAdapter;
import com.example.notesappwithmvvm.ViewModel.NotesViewModel;
import com.example.notesappwithmvvm.Model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView; // Updated to the correct import

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton newNotesbtn;
    private NotesViewModel notesViewModel;
    private RecyclerView notesRecycleView;
    private LinearLayout lowtohigh, hightolow, NoFilter;
    private NotesAdapter adapter;
    private List<Notes> allNotes; // Store all notes for search filtering

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView and ViewModel
        notesRecycleView = findViewById(R.id.notesRecycleView);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        // Initialize filter buttons (LinearLayouts)
        lowtohigh = findViewById(R.id.lowtohigh);
        hightolow = findViewById(R.id.hightolow);
        NoFilter = findViewById(R.id.NoFilter);
        NoFilter.setBackgroundResource(R.drawable.select);  // Default "No Filter" selection

        // Observe the LiveData from ViewModel and set up the adapter
        notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                allNotes = new ArrayList<>(notes); // Store all notes
                setAdapter(notes);  // Set the adapter with all notes
            }
        });

        // Filter button click listeners
        NoFilter.setOnClickListener(view -> setFilter(0));
        hightolow.setOnClickListener(view -> setFilter(1));
        lowtohigh.setOnClickListener(view -> setFilter(2));

        // Set ActionBar color and title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF5151")));
            getSupportActionBar().setTitle("Notes");
        }

        // FloatingActionButton click listener for adding new note
        newNotesbtn = findViewById(R.id.newNotesbtn);
        newNotesbtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, InsertNoteActivity.class)));
    }

    // Helper method to set the adapter for RecyclerView
    private void setAdapter(List<Notes> notes) {
        notesRecycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotesAdapter(MainActivity.this, notes);
        notesRecycleView.setAdapter(adapter);
    }

    // Helper method to update filter background
    private void setFilterBackground(int filterOption) {
        hightolow.setBackgroundResource(R.drawable.filter_shape);
        lowtohigh.setBackgroundResource(R.drawable.filter_shape);
        NoFilter.setBackgroundResource(R.drawable.filter_shape);

        if (filterOption == 0) {
            NoFilter.setBackgroundResource(R.drawable.select);
        } else if (filterOption == 1) {
            hightolow.setBackgroundResource(R.drawable.select);
        } else if (filterOption == 2) {
            lowtohigh.setBackgroundResource(R.drawable.select);
        }
    }

    // Set the filter option and observe the data
    private void setFilter(int filterOption) {
        setFilterBackground(filterOption);

        if (filterOption == 0) {
            notesViewModel.getAllNotes().observe(this, notes -> setAdapter(notes));  // Show all notes
        } else if (filterOption == 1) {
            notesViewModel.getHighToLow().observe(this, notes -> setAdapter(notes));  // High to Low sorting
        } else if (filterOption == 2) {
            notesViewModel.getLowToHigh().observe(this, notes -> setAdapter(notes));  // Low to High sorting
        }
    }

    // Method to filter notes based on the search query
    private void NotesFilter(String query) {
        try {
            if (allNotes != null && adapter != null) {
                List<Notes> filteredList = new ArrayList<>();
                for (Notes note : allNotes) {
                    if (note.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                            note.getSubtitle().toLowerCase().contains(query.toLowerCase()) ||
                            note.getNotes().toLowerCase().contains(query.toLowerCase())) {
                        filteredList.add(note);
                    }
                }
                adapter.filterList(filteredList); // Update the adapter with filtered list
            }
        } catch (Exception e) {
            Log.e("FilterError", "Error while filtering notes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView(); // Use correct SearchView class
        searchView.setQueryHint("Search Notes Here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                NotesFilter(s); // Call the filtering method on text change
                return false;
            }
        });
        return true;
    }
}
