package com.example.ideator.ui.ideas;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideator.R;
import com.example.ideator.model.idea.Idea;
import com.example.ideator.model.idea.IdeaWithSections;
import com.example.ideator.model.section.Section;
import com.example.ideator.ui.edit_idea.EditIdeaActivity;
import com.example.ideator.utils.openai.BusinessPlanningAssistant;

/**
 * A fragment representing a list of Items.
 */
public class IdeasFragment extends Fragment {
    public static final int EDIT_IDEA_REQUEST = 1;
    private IdeasViewModel ideaViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IdeasFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        RecyclerView recyclerView = view.findViewById(R.id.list_ideas);
        Context context = recyclerView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        IdeasAdapter ideaAdapter = new IdeasAdapter();
        recyclerView.setAdapter(ideaAdapter);

        //Set the view model
        ideaViewModel = new ViewModelProvider(this).get(IdeasViewModel.class);
        ideaViewModel.getAll().observe(getActivity(), ideas -> {
            ideaAdapter.setIdeas(ideas);

            TextView emptyText = view.findViewById(R.id.text_ideas);
            if (ideas.isEmpty()) {
                emptyText.setVisibility(View.VISIBLE);
            }
            else {
                emptyText.setVisibility(View.INVISIBLE);
            }
        });

        //Set the add button listener
        View buttonAddIdea = view.findViewById(R.id.button_add_idea);
        buttonAddIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText descriptionText = new EditText(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.new_idea);
                builder.setMessage(R.string.new_idea_instruction);
                builder.setView(descriptionText);
                builder.setPositiveButton(R.string.create_idea, (DialogInterface.OnClickListener) (dialog, whichButton) -> {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage(getContext().getText(R.string.create_idea_loading));
                    progressDialog.setCancelable(false);
                    progressDialog.setInverseBackgroundForced(false);

                    new BusinessPlanningAssistant().getTitleDescriptionProblematicSolution(
                        getActivity(),
                        descriptionText.getText().toString(),
                        new BusinessPlanningAssistant.OnResponse() {
                            @Override
                            public void onSuccess(String title, String description, String problematic, String solution) {
                                progressDialog.hide();
                                IdeaWithSections ideaWithSections = new IdeaWithSections(new Idea(title, description));
                                ideaWithSections.sections.add(Section.createProblematic(problematic));
                                ideaWithSections.sections.add(Section.createSolution(solution));
                                ideaViewModel.insert(ideaWithSections, id -> {
                                    openEditIdeaActivity(id);
                                });
                            }

                            @Override
                            public void onError(Throwable error) {
                                progressDialog.hide();
                                error.printStackTrace();
                                Toast.makeText(getActivity(),
                                        R.string.error_offline,
                                        Toast.LENGTH_LONG).show();

                                Idea idea = new Idea(descriptionText.getText().toString());
                                ideaViewModel.insert(idea, id -> {
                                    openEditIdeaActivity(id);
                                });
                            }
                        });
                    progressDialog.show();
                });
                builder.create().show();
            }
        });

        ideaAdapter.setOnItemClickListener(new IdeasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(IdeaWithSections idea) {
                openEditIdeaActivity(idea.idea.getId());
            }
        });

        return view;
    }

    private void openEditIdeaActivity(long id) {
        Intent intent = new Intent(getActivity(), EditIdeaActivity.class);
        intent.putExtra(EditIdeaActivity.EXTRA_ID, id);
        startActivityForResult(intent, EDIT_IDEA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_LONG).show();
        }
    }
}