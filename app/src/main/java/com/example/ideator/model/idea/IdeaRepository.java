package com.example.ideator.model.idea;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class IdeaRepository {
    private IdeaDao ideaDao;
    private LiveData<List<IdeaWithSections>> ideas;

    public IdeaRepository(Application application) {
        IdeaDatabase database = IdeaDatabase.getInstance(application);
        ideaDao = database.ideaDao();
        ideas = ideaDao.getAll();
    }

    public void insert(Idea idea) {
        new InsertIdeaAsyncTask(ideaDao).execute(idea);
    }

    public void update(Idea idea) {
        new UpdateIdeaAsyncTask(ideaDao).execute(idea);
    }

    public void delete(Idea idea) {
        new DeleteIdeaAsyncTask(ideaDao).execute(idea);
    }

    public LiveData<List<IdeaWithSections>> getAll() {
        return ideas;
    }

    private static class InsertIdeaAsyncTask extends AsyncTask<Idea, Void, Void> {
        private IdeaDao ideaDao;

        private InsertIdeaAsyncTask(IdeaDao ideaDao) {
            this.ideaDao = ideaDao;
        }

        @Override
        protected Void doInBackground(Idea... ideas) {
            ideaDao.insert(ideas[0]);
            return null;
        }
    }

    private static class UpdateIdeaAsyncTask extends AsyncTask<Idea, Void, Void> {
        private IdeaDao ideaDao;

        private UpdateIdeaAsyncTask(IdeaDao ideaDao) {
            this.ideaDao = ideaDao;
        }

        @Override
        protected Void doInBackground(Idea... ideas) {
            ideaDao.update(ideas[0]);
            return null;
        }
    }

    private static class DeleteIdeaAsyncTask extends AsyncTask<Idea, Void, Void> {
        private IdeaDao ideaDao;

        private DeleteIdeaAsyncTask(IdeaDao ideaDao) {
            this.ideaDao = ideaDao;
        }

        @Override
        protected Void doInBackground(Idea... ideas) {
            ideaDao.delete(ideas[0]);
            return null;
        }
    }
}
