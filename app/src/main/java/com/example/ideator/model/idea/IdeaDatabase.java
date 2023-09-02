package com.example.ideator.model.idea;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ideator.model.section.Section;

@Database(entities = {Idea.class, Section.class}, version = 2)
public abstract class IdeaDatabase extends RoomDatabase {

    private static IdeaDatabase instance;

    public abstract IdeaDao ideaDao();

    public static synchronized IdeaDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            IdeaDatabase.class, "idea_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private IdeaDao ideaDao;

        private PopulateDbAsyncTask(IdeaDatabase db) {
            ideaDao = db.ideaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ideaDao.insert(new Idea("Title 1", "Description 1"));
            ideaDao.insert(new Idea("Title 2", "Description 2"));
            ideaDao.insert(new Idea("Title 3", "Description 3"));
            return null;
        }
    }
}
