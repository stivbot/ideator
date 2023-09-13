package com.example.ideator.model.idea;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.ideator.model.section.Section;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class IdeaRepository {
    private IdeaDao ideaDao;
    private LiveData<List<IdeaWithSections>> ideas;

    private Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public IdeaRepository(Application application) {
        IdeaDatabase database = IdeaDatabase.getInstance(application);
        ideaDao = database.ideaDao();
        ideas = ideaDao.getAll();
    }

    public void insert(Idea idea, OnInsertResponse onResponce) {
        executor.execute(() -> {
            long id = ideaDao.insert(idea);
            handler.post(() -> {
                if (onResponce != null) {
                    onResponce.onInsert(id);
                }
            });
        });
    }

    public void insert(IdeaWithSections ideaWithSections, OnInsertResponse onResponce) {
        executor.execute(() -> {
            long id = ideaDao.insert(ideaWithSections.idea);
            for (Section section:ideaWithSections.sections) {
                section.setIdeaId(id);
            }
            ideaDao.insert(ideaWithSections.sections);

            handler.post(() -> {
                if (onResponce != null) {
                    onResponce.onInsert(id);
                }
            });
        });
    }

    public void update(Idea idea) {
        executor.execute(() -> {
            ideaDao.update(idea);
        });
    }

    public void update(IdeaWithSections ideaWithSections) {
        executor.execute(() -> {
            ideaDao.update(ideaWithSections.idea);
            for (Section section:ideaWithSections.sections) {
                section.setIdeaId(ideaWithSections.idea.getId());
            }
            ideaDao.update(ideaWithSections.sections);
        });
    }

    public void delete(Idea idea) {
        executor.execute(() -> {
            ideaDao.delete(idea);
        });
    }

    public LiveData<List<IdeaWithSections>> getAll() {
        return ideas;
    }

    public LiveData<IdeaWithSections> get(long id) {
        return ideaDao.get(id);
    }

    public interface OnInsertResponse {
        void onInsert(long id);
    }
}
