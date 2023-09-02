package com.example.ideator.ui.ideas;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ideator.R;
import com.example.ideator.model.idea.IdeaWithSections;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class IdeasFragment extends Fragment {
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
                Snackbar.make(view, "New idea button triggered", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }
}