package com.example.ideator.ui.edit_idea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ideator.R;
import com.example.ideator.model.idea.IdeaWithSections;
import com.example.ideator.ui.ideas.IdeasViewModel;

public class EditIdeaActivity extends AppCompatActivity {
    public static final int RESULT_DELETE = 1;
    public static final long INVALID_ID = -1;
    public static final String EXTRA_ID = "com.example.ideator.ui.edit_idea.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.ideator.ui.edit_idea.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.ideator.ui.edit_idea.EXTRA_DESCRIPTION";
    public static final String EXTRA_PROBLEMATIC = "com.example.ideator.ui.edit_idea.EXTRA_PROBLEMATIC";
    public static final String EXTRA_SOLUTION = "com.example.ideator.ui.edit_idea.EXTRA_SOLUTION";

    private EditText titleText;
    private EditText descriptionText;
    private EditIdeaViewModel editIdeaViewModel;

    private IdeaWithSections idea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_idea);

        //Get fields
        titleText = findViewById(R.id.edit_idea_title);
        descriptionText = findViewById(R.id.edit_idea_description);

        //Set the adapter TODO

        //Set the view model
        Intent intent = getIntent();
        long id = intent.getLongExtra(EXTRA_ID, INVALID_ID);

        editIdeaViewModel = new ViewModelProvider(this).get(EditIdeaViewModel.class);
        editIdeaViewModel.get(id).observe(this, idea -> {
            this.idea = idea;
            if (idea != null) {
                //Set title
                titleText.setText(idea.idea.getTitle());

                //Set description
                descriptionText.setText(idea.idea.getDescription());

                //Set sections
                //sectionAdapter.setSections(idea.sections); TODO
            }
        });

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
        if (item.getItemId() == R.id.delete_idea_button) {
            confirmDeleteIdea();
            return true;
        }
        else {
            idea.idea.setTitle(titleText.getText().toString());
            idea.idea.setDescription(descriptionText.getText().toString());
            editIdeaViewModel.update(idea.idea);
            setResult(RESULT_OK);
            finish();
            Toast.makeText(this, "Idea saved", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private void confirmDeleteIdea() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you when to delete the idea? This action cannot be reversed!");
        builder.setPositiveButton("Confirm", (dialog, which) -> {
            editIdeaViewModel.delete(idea.idea);
            setResult(RESULT_OK);
            finish();
            Toast.makeText(this, "Idea deleted", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            //Nothing to do
        });
        builder.create().show();
    }
}