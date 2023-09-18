package com.example.ideator.ui.ideas;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ideator.model.idea.Idea;
import com.example.ideator.model.idea.IdeaRepository;
import com.example.ideator.model.idea.IdeaWithSections;

import java.util.List;

public class IdeasViewModel extends AndroidViewModel {
    private IdeaRepository repository;
    private LiveData<List<IdeaWithSections>> ideas;

    public IdeasViewModel(@NonNull Application application) {
        super(application);
        repository = new IdeaRepository(application);
        ideas = repository.getAll();
    }

    public void insert(Idea idea, IdeaRepository.OnInsertResponse onResponse) {
        repository.insert(idea, onResponse);
    }

    public void insert(IdeaWithSections ideaWithSections, IdeaRepository.OnInsertResponse onResponse) {
        repository.insert(ideaWithSections, onResponse);
    }

    public void update(Idea idea) {
        repository.update(idea, null);
    }

    public void delete(Idea idea) {
        repository.delete(idea);
    }

    public LiveData<List<IdeaWithSections>> getAll() {
        return ideas;
    }
}
