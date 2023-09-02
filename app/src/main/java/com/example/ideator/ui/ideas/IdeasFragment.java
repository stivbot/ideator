package com.example.ideator.ui.ideas;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ideator.R;
import com.example.ideator.model.idea.IdeaWithSections;
import com.example.ideator.ui.ideas.placeholder.PlaceholderContent;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class IdeasFragment extends Fragment {
    private IdeaViewModel ideaViewModel;


    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IdeasFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static IdeasFragment newInstance(int columnCount) {
        IdeasFragment fragment = new IdeasFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ideaViewModel = new ViewModelProvider(this).get(IdeaViewModel.class);
        ideaViewModel.getAll().observe(this, new Observer<List<IdeaWithSections>>() {
            @Override
            public void onChanged(List<IdeaWithSections> ideaWithSections) {
                //Update RecyclerView
                Toast.makeText(getActivity(), "onChanged", Toast.LENGTH_SHORT).show();
            }
        });

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        RecyclerView recyclerView = view.findViewById(R.id.list_ideas);
        Context context = recyclerView.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(new MyIdeasRecyclerViewAdapter(PlaceholderContent.ITEMS));

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