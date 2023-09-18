package com.example.ideator.ui.edit_idea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideator.R;
import com.example.ideator.databinding.FragmentSectionBinding;
import com.example.ideator.model.idea.Idea;
import com.example.ideator.model.idea.IdeaRepository;
import com.example.ideator.model.idea.IdeaWithSections;
import com.example.ideator.model.section.Section;
import com.example.ideator.ui.ideas.IdeasAdapter;
import com.example.ideator.ui.ideas.IdeasViewModel;
import com.example.ideator.utils.openai.BusinessPlanningAssistant;

public class EditIdeaActivity extends AppCompatActivity {
    public static final long INVALID_ID = -1;
    public static final String EXTRA_ID = "com.example.ideator.ui.edit_idea.EXTRA_ID";

    private EditText titleText;
    private EditText descriptionText;
    private EditIdeaViewModel editIdeaViewModel;

    private IdeaWithSections idea;
    private RecyclerView recyclerView;
    private SectionsAdapter sectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_idea);

        //Get fields
        titleText = findViewById(R.id.edit_idea_title);
        descriptionText = findViewById(R.id.edit_idea_description);

        //Set the adapter
        recyclerView = findViewById(R.id.list_sections);
        Context context = recyclerView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        sectionAdapter = new SectionsAdapter();
        recyclerView.setAdapter(sectionAdapter);

        //Set the adapter
        Button nextButton = findViewById(R.id.button_next_idea);
        nextButton.setOnClickListener(v -> {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(this.getText(R.string.create_idea_loading));
            progressDialog.setCancelable(false);
            progressDialog.setInverseBackgroundForced(false);

            saveIdea(() -> {
                new BusinessPlanningAssistant().getProsCons(
                   this,
                    idea,
                    new BusinessPlanningAssistant.OnProsConsResponse() {
                        @Override
                        public void onSuccess(String pros, String cons) {
                            progressDialog.hide();
                            idea.sections.add(Section.createPros(pros));
                            idea.sections.add(Section.createCons(cons));
                            //TODO sections not added to the idea, use onCreateView instead ?
                            editIdeaViewModel.update(idea, null);
                        }

                        @Override
                        public void onError(Throwable error) {
                            progressDialog.hide();
                            error.printStackTrace();
                            Toast.makeText(EditIdeaActivity.this,
                                R.string.error_offline,
                                Toast.LENGTH_LONG).show();
                        }
                    });
            });
            progressDialog.show();
        });

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
                sectionAdapter.setSections(idea.sections);
            }
        });

        //TODO load idea title and make in editable
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
            deleteIdea();
            return true;
        }
        else {
            saveIdea(null);
            setResult(RESULT_OK);
            finish();
            Toast.makeText(this, R.string.saved_idea, Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private void deleteIdea() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.delete_idea);
        builder.setMessage(R.string.delete_idea_confirm);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            editIdeaViewModel.delete(idea.idea);
            setResult(RESULT_OK);
            finish();
            Toast.makeText(this, R.string.deleted_idea, Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            //Nothing to do
        });
        builder.create().show();
    }

    private void saveIdea(IdeaRepository.OnUpdateResponse onResponse) {
        idea.idea.setTitle(titleText.getText().toString());
        idea.idea.setDescription(descriptionText.getText().toString());

        //Use onFocusChanged in adapter instead ? TODO
        for (int i=0; i<sectionAdapter.getItemCount(); i++) {
            View sectionView = recyclerView.getLayoutManager().findViewByPosition(i);
            if (sectionView != null) {
                String title = ((TextView) sectionView.findViewById(R.id.section_name)).getText().toString();
                String description = ((TextView) sectionView.findViewById(R.id.section_description)).getText().toString();

                idea.sections.get(i).setTitle(title);
                idea.sections.get(i).setDescription(description);
            }
        }

        editIdeaViewModel.update(idea, onResponse);
    }
}