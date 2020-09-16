package com.example.noteproject.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.noteproject.R;
import com.example.noteproject.data.NoteViewModel;
import com.example.noteproject.databse.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NoteAdapter.ItemClickListener {
    private static final int ADD_REQUST_CODE = 1;
    private static final int EDITE_REQUEST_CODE=2;
    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_veiw);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });
        floatingActionButton.setOnClickListener(this);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, EditAddNoteActivity.class);
        startActivityForResult(intent, ADD_REQUST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_REQUST_CODE && resultCode == RESULT_OK) {

            String title, descrption;
            int priority = data.getIntExtra(EditAddNoteActivity.PRIORITY, 1);
            title = data.getStringExtra(EditAddNoteActivity.TITLE);
            descrption = data.getStringExtra(EditAddNoteActivity.DESCRIPTION);
            Note note = new Note(priority, title, descrption);
            noteViewModel.insert(note);
            return;
        }
        if(requestCode == EDITE_REQUEST_CODE && resultCode == RESULT_OK)
        {
            String title, descrption;
            int priority = data.getIntExtra(EditAddNoteActivity.PRIORITY, 1);
            int id=data.getIntExtra(EditAddNoteActivity.ID,-1);
            title = data.getStringExtra(EditAddNoteActivity.TITLE);
            descrption = data.getStringExtra(EditAddNoteActivity.DESCRIPTION);
            Note note = new Note(priority, title, descrption);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this,"Note Updated",Toast.LENGTH_SHORT).show();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delelte_all_notes:
                noteViewModel.deleteAll();
                return true;
            default:
        return super.onOptionsItemSelected(item);

    }}

    @Override
    public void onItemClick(Note note) {
        Intent intent =new Intent(this, EditAddNoteActivity.class);
        intent.putExtra(EditAddNoteActivity.ID,note.getId());
        intent.putExtra(EditAddNoteActivity.TITLE,note.getTitle());
        intent.putExtra(EditAddNoteActivity.DESCRIPTION,note.getDescription());
        intent.putExtra(EditAddNoteActivity.PRIORITY,note.getPariorty());
       // Toast.makeText(this,String.valueOf(note.getPariorty()),Toast.LENGTH_LONG).show();
        startActivityForResult(intent,EDITE_REQUEST_CODE);
    }
}