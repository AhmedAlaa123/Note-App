package com.example.noteproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.noteproject.R;

public class EditAddNoteActivity extends AppCompatActivity {

    public static final String ID = "com.example.noteproject.ui.ID";
    public static final String TITLE = "com.example.noteproject.ui.title";
    public static final String DESCRIPTION = "com.example.noteproject.ui.description";
    public static final String PRIORITY = "com.example.noteproject.ui.priority";
    private EditText editText_title, editText_description;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editText_title = findViewById(R.id.edit_text_title);
        editText_description = findViewById(R.id.edit_text_description);
        numberPicker = findViewById(R.id.number_picker);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        if (getIntent().hasExtra(ID)) {
            setTitle("Edit Note");
            editText_title.setText(getIntent().getStringExtra(TITLE));
            editText_description.setText(getIntent().getStringExtra(DESCRIPTION));
            int priority = getIntent().getIntExtra(PRIORITY, 1);
            numberPicker.setValue(priority);
        } else
            setTitle("Add Note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.save_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        //saving note
        String title, description;
        int priority = numberPicker.getValue();
        title = editText_title.getText().toString();
        description = editText_description.getText().toString();
        if (title.trim().isEmpty()) {
            editText_title.setError("Title required");
            return;
        }
        if (description.trim().isEmpty()) {
            editText_description.setError("Description is required");
            return;
        }
        Intent intent = new Intent();
        if (getIntent().hasExtra(ID))//this condition to check if operation is update or insert
        {
            intent.putExtra(ID, getIntent().getIntExtra(ID, 1));
        }
        intent.putExtra(TITLE, title);
        intent.putExtra(DESCRIPTION, description);
        intent.putExtra(PRIORITY, priority);
        setResult(RESULT_OK, intent);
        finish();


    }
}