package com.example.ideator.ui.ideas;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ideator.R;
import com.example.ideator.model.idea.Idea;
import com.example.ideator.model.idea.IdeaWithSections;
import com.example.ideator.ui.edit_idea.EditIdeaActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class IdeasFragment extends Fragment {
    public static final int ADD_IDEA_REQUEST = 1;
    public static final int EDIT_IDEA_REQUEST = 2;
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
        ideaViewModel.getAll().observe(getActivity(), new Observer<List<IdeaWithSections>>() {
            @Override
            public void onChanged(List<IdeaWithSections> ideas) {
                ideaAdapter.setIdeas(ideas);
            }
        });

        //Set the add button listener
        View buttonAddIdea = view.findViewById(R.id.button_add_idea);
        buttonAddIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditIdeaActivity.class);
                startActivityForResult(intent, ADD_IDEA_REQUEST);
            }
        });

        ideaAdapter.setOnItemClickListener(new IdeasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(IdeaWithSections idea) {
                Intent intent = new Intent(getActivity(), EditIdeaActivity.class);
                intent.putExtra(EditIdeaActivity.EXTRA_ID, idea.idea.getId());
                intent.putExtra(EditIdeaActivity.EXTRA_TITLE, idea.idea.getTitle());
                intent.putExtra(EditIdeaActivity.EXTRA_DESCRIPTION, idea.idea.getDescription());
                startActivityForResult(intent, EDIT_IDEA_REQUEST);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_IDEA_REQUEST) {
                add(data);
            }
            else if (requestCode == EDIT_IDEA_REQUEST) {
                edit(data);
            }
        }
        else if (resultCode == RESULT_CANCELED) {
            delete(data);
        }
    }

    private void add(Intent data) {
        String title = data.getStringExtra(EditIdeaActivity.EXTRA_TITLE);
        String description = data.getStringExtra(EditIdeaActivity.EXTRA_DESCRIPTION);
        Idea idea = new Idea(title, description);
        ideaViewModel.insert(idea);
        Toast.makeText(getActivity(), "Idea saved", Toast.LENGTH_SHORT).show();
    }

    private void edit(Intent data) {
        int id = data.getIntExtra(EditIdeaActivity.EXTRA_ID, EditIdeaActivity.INVALID_ID);
        if (id == EditIdeaActivity.INVALID_ID) {
            Toast.makeText(getActivity(), "Could not save the idea. Something went wrong.", Toast.LENGTH_SHORT).show();
            return;
        }
        String title = data.getStringExtra(EditIdeaActivity.EXTRA_TITLE);
        String description = data.getStringExtra(EditIdeaActivity.EXTRA_DESCRIPTION);
        Idea idea = new Idea(title, description);
        idea.setId(id);
        ideaViewModel.update(idea);
        Toast.makeText(getActivity(), "Idea saved", Toast.LENGTH_SHORT).show();
    }

    private void delete(Intent data) {
        int id = data.getIntExtra(EditIdeaActivity.EXTRA_ID, EditIdeaActivity.INVALID_ID);
        if (id == EditIdeaActivity.INVALID_ID) {
            Toast.makeText(getActivity(), "Could not delete the idea. Something went wrong.", Toast.LENGTH_SHORT).show();
            return;
        }
        Idea idea = new Idea(null, null);
        idea.setId(id);
        ideaViewModel.delete(idea);
        Toast.makeText(getActivity(), "Idea deleted", Toast.LENGTH_SHORT).show();
    }
}