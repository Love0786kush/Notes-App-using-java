package com.example.notesappwithmvvm.Activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesappwithmvvm.Model.Notes;
import com.example.notesappwithmvvm.R;
import com.example.notesappwithmvvm.ViewModel.NotesViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Date;

public class UpdateNoteActivity extends AppCompatActivity {

    private EditText titleEditText, subtitleEditText, notesEditText;
    private ImageView priorityRed, priorityYellow, priorityGreen;
    private String priority = "1";

    private int noteId;
    private NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF5151")));
            getSupportActionBar().setTitle("Notes");
        }

        titleEditText = findViewById(R.id.titleEditText);
        subtitleEditText = findViewById(R.id.subtitleEditText);
        notesEditText = findViewById(R.id.notesEditText);
        priorityRed = findViewById(R.id.priorityRed);
        priorityYellow = findViewById(R.id.priorityYellow);
        priorityGreen = findViewById(R.id.priorityGreen);
        FloatingActionButton updateNoteBtn = findViewById(R.id.updateNotebtn);

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        noteId = getIntent().getIntExtra("id", -1);
        String sTitle = getIntent().getStringExtra("Title");
        String sSubtitle = getIntent().getStringExtra("Subtitle");
        String sNotes = getIntent().getStringExtra("Notes");
        String sPriority = getIntent().getStringExtra("Priority");

        if (noteId == -1) {
            Toast.makeText(this, "Note ID is missing. Cannot update note.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        titleEditText.setText(sTitle);
        subtitleEditText.setText(sSubtitle);
        notesEditText.setText(sNotes);

        if (sPriority != null) {
            priority = sPriority;
            setPriorityIcon(sPriority);
        } else {
            setPriority("1");
        }

        priorityRed.setOnClickListener(v -> setPriority("3"));
        priorityYellow.setOnClickListener(v -> setPriority("2"));
        priorityGreen.setOnClickListener(v -> setPriority("1"));

        updateNoteBtn.setOnClickListener(v -> {
            String updatedTitle = titleEditText.getText().toString().trim();
            String updatedSubtitle = subtitleEditText.getText().toString().trim();
            String updatedNotes = notesEditText.getText().toString().trim();

            if (updatedTitle.isEmpty() && updatedSubtitle.isEmpty() && updatedNotes.isEmpty()) {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
                return;
            }

            String currentDate = DateFormat.getDateInstance().format(new Date());

            Notes note = new Notes();
            note.setId(noteId);
            note.setTitle(updatedTitle);
            note.setSubtitle(updatedSubtitle + " • " + currentDate);
            note.setNotes(updatedNotes);

            try {
                note.setPriority(Integer.parseInt(priority));
            } catch (NumberFormatException e) {
                note.setPriority(1);
            }

            notesViewModel.updateNote(note);
            Toast.makeText(this, "Note Updated Successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void setPriority(String value) {
        priority = value;
        priorityRed.setImageResource(value.equals("3") ? R.drawable.doneicon : 0);
        priorityYellow.setImageResource(value.equals("2") ? R.drawable.doneicon : 0);
        priorityGreen.setImageResource(value.equals("1") ? R.drawable.doneicon : 0);
    }

    private void setPriorityIcon(String value) {
        if (value != null) setPriority(value);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deletemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ic_delete) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(UpdateNoteActivity.this, R.style.BottomSheetStyle);

            // ✅ DO NOT attach a parent here — fixes the crash
            View view = LayoutInflater.from(UpdateNoteActivity.this)
                    .inflate(R.layout.deletebottomsheet, null);

            bottomSheetDialog.setContentView(view);

            TextView yesButton = view.findViewById(R.id.yesButton);
            TextView noButton = view.findViewById(R.id.noButton);

            yesButton.setOnClickListener(v -> {
                ProgressDialog progressDialog = new ProgressDialog(UpdateNoteActivity.this);
                progressDialog.setMessage("Deleting note...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                new android.os.Handler().postDelayed(() -> {
                    notesViewModel.deleteNote(noteId);
                    progressDialog.dismiss();
                    Toast.makeText(UpdateNoteActivity.this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }, 1000);
            });

            noButton.setOnClickListener(v -> bottomSheetDialog.dismiss());

            bottomSheetDialog.show();
        }
        return true;
    }
}





