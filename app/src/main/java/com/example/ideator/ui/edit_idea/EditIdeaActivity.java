package com.example.ideator.ui.edit_idea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.ideator.R;

public class EditIdeaActivity extends AppCompatActivity {
    public static final int INVALID_ID = -1;
    public static final String EXTRA_ID = "com.example.ideator.ui.edit_idea.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.ideator.ui.edit_idea.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.ideator.ui.edit_idea.EXTRA_DESCRIPTION";

    private EditText titleText;
    private EditText descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_idea);

        titleText = findViewById(R.id.edit_idea_title);
        descriptionText = findViewById(R.id.edit_idea_description);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit idea");
            titleText.setText(intent.getStringExtra(EXTRA_TITLE));
            descriptionText.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        }
        else {
            setTitle("New idea");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_idea_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_idea_button) {
            deleteIdea();
            return true;
        }
        else {
            saveIdea();
            return true;
        }
    }

    private void saveIdea() {
        String title = titleText.getText().toString();
        String description = descriptionText.getText().toString();

        Intent data = new Intent();
        int id = getIntent().getIntExtra(EXTRA_ID, INVALID_ID);
        if (id != INVALID_ID) {
            data.putExtra(EXTRA_ID, id);
        }
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);

        setResult(RESULT_OK, data);
        finish();
    }

    private void deleteIdea() {
        Intent data = new Intent();
        int id = getIntent().getIntExtra(EXTRA_ID, INVALID_ID);
        if (id != INVALID_ID) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_CANCELED, data);
        finish();
    }
}