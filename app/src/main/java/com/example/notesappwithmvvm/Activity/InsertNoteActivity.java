package com.example.notesappwithmvvm.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesappwithmvvm.R;
import com.example.notesappwithmvvm.ViewModel.NotesViewModel;
import com.example.notesappwithmvvm.databinding.ActivityInsertNoteBinding;
import com.example.notesappwithmvvm.Model.Notes;

import java.text.DateFormat;
import java.util.Date;

public class InsertNoteActivity extends AppCompatActivity {

    ActivityInsertNoteBinding binding;
    String title, subtitle, notesContent;
    NotesViewModel notesViewModel;
    String Priority = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize view binding
        binding = ActivityInsertNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set ActionBar background color and title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF5151")));
            getSupportActionBar().setTitle("Notes");
        }

        // Initialize ViewModel
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);



        binding.priorityGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.priorityGreen.setImageResource(R.drawable.doneicon);
                binding.priorityRed.setImageResource(0);
                binding.priorityYellow.setImageResource(0);
                Priority = "1";

            }
        });
        binding.priorityRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.priorityRed.setImageResource(R.drawable.doneicon);
                binding.priorityGreen.setImageResource(0);
                binding.priorityYellow.setImageResource(0);
                Priority = "3";
            }
        });
    binding.priorityYellow.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            binding.priorityYellow.setImageResource(R.drawable.doneicon);
            binding.priorityRed.setImageResource(0);
            binding.priorityGreen.setImageResource(0);
            Priority = "2";
        }
    });
        // Handle "Done" button click
        binding.doneNotesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = binding.notesData.getText().toString().trim();
                subtitle = binding.notesSubtitle.getText().toString().trim();
                notesContent = binding.notesData.getText().toString().trim();

                // Validate input
                if (!title.isEmpty() || !subtitle.isEmpty() || !notesContent.isEmpty()) {
                    createNote(title, subtitle, notesContent);
                    finish(); // Close activity after saving
                } else {
                    binding.notesData.setError("Please enter something");
                }
            }
        });
    }

    private void createNote(String title, String subtitle, String notesContent) {
        Date date = new Date();
        String currentDate = DateFormat.getDateInstance().format(date);
        Notes note = new Notes();
        note.setTitle(title);
        note.setSubtitle(subtitle + " • " + currentDate);
        note.setNotes(notesContent);
        note.setPriority(Integer.parseInt(Priority));


        // Insert into database via ViewModel
        notesViewModel.insertNote(note);

        Toast.makeText(this, "Notes Create Successfully", Toast.LENGTH_SHORT).show();
    finish();
    }
}
