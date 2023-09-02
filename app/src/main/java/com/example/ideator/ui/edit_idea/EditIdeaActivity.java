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

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_profile);
        setTitle("Edit idea");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_idea_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_idea_button) {
            saveIdea();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void saveIdea() {
        String title = titleText.getText().toString();
        String description = descriptionText.getText().toString();

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);

        setResult(RESULT_OK, data);
        finish();
    }
}