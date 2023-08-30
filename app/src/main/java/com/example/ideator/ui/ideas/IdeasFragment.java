package com.example.ideator.ui.ideas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ideator.databinding.FragmentIdeasBinding;

public class IdeasFragment extends Fragment {

    private FragmentIdeasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IdeasViewModel ideasViewModel =
                new ViewModelProvider(this).get(IdeasViewModel.class);

        binding = FragmentIdeasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textIdeas;
        ideasViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}