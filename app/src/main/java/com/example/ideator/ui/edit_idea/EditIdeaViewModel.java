package com.example.ideator.ui.edit_idea;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ideator.model.idea.Idea;
import com.example.ideator.model.idea.IdeaRepository;
import com.example.ideator.model.idea.IdeaWithSections;

public class EditIdeaViewModel extends AndroidViewModel {
    private final IdeaRepository repository;

    public EditIdeaViewModel(@NonNull Application application) {
        super(application);
        repository = new IdeaRepository(application);
    }

    public void update(IdeaWithSections ideaWithSections) {
        repository.update(ideaWithSections);
    }

    public void delete(Idea idea) {
        repository.delete(idea);
    }

    public LiveData<IdeaWithSections> get(long id) {
        return repository.get(id);
    }
}
