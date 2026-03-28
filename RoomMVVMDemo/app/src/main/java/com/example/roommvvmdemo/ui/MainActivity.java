package com.example.roommvvmdemo.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roommvvmdemo.R;
import com.example.roommvvmdemo.data.local.Note;
import com.example.roommvvmdemo.viewmodel.NoteViewModel;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel viewModel;
    private EditText inputTitle;
    private EditText inputContent;
    private Button addBtn;
    private Button clearBtn;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputTitle = findViewById(R.id.input_heading);
        inputContent = findViewById(R.id.input_content);
        addBtn = findViewById(R.id.btn_save_note);
        clearBtn = findViewById(R.id.btn_clear_all);

        RecyclerView listView = findViewById(R.id.notes_list_view);

        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setHasFixedSize(true);

        noteAdapter = new NoteAdapter();
        listView.setAdapter(noteAdapter);

        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        viewModel.observeNotes().observe(this, list -> {
            noteAdapter.updateData(list);
        });

        addBtn.setOnClickListener(v -> createNote());

        clearBtn.setOnClickListener(v -> {
            viewModel.deleteEverything();
            Toast.makeText(this, "Toutes les notes supprimées", Toast.LENGTH_SHORT).show();
        });

        noteAdapter.setHoldListener(item -> {
            viewModel.deleteNote(item);
            Toast.makeText(this, "Note supprimée", Toast.LENGTH_SHORT).show();
        });

        noteAdapter.setTapListener(item -> {
            Toast.makeText(this, "Titre : " + item.getNoteTitle(), Toast.LENGTH_SHORT).show();
        });
    }

    private void createNote() {
        String t = inputTitle.getText().toString().trim();
        String c = inputContent.getText().toString().trim();

        if (t.isEmpty() || c.isEmpty()) {
            Toast.makeText(this, "Champs vides", Toast.LENGTH_SHORT).show();
            return;
        }

        Note newItem = new Note(t, c);
        viewModel.addNote(newItem);

        inputTitle.setText("");
        inputContent.setText("");

        Toast.makeText(this, "Note ajoutée", Toast.LENGTH_SHORT).show();
    }
}