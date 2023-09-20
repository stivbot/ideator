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
                    .build();
        }
        return instance;
    }
}
