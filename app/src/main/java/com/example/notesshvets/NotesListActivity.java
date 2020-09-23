package com.example.notesshvets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotesListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ItemDataAdapter adapter;
    private FloatingActionButton fab;
    private ListView listView;
    private final String INTENT_DATA = "positionAdapter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(NotesListActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        FloatingActionButton fab = findViewById(R.id.fab);
        ListView listView = findViewById(R.id.listView);

        adapter = new ItemDataAdapter(this, null);
        listView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentMove(-1);// Передаем несуществующую позицию адаптера равную -1 в RecordActivity


            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intentMove(position);
            }
        });

    }

    private void intentMove(int position) {
        Intent intent = new Intent(NotesListActivity.this, RecordingActivity.class);
        intent.putExtra(INTENT_DATA, position);
        startActivity(intent);

    }
}
