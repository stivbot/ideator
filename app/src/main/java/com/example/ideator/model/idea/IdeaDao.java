package com.example.ideator.model.idea;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface IdeaDao {

    @Insert
    void insert(Idea idea);

    @Insert
    void update(Idea idea);

    @Insert
    void delete(Idea idea);

    @Transaction
    @Query("Select * FROM idea_table")
    LiveData<List<IdeaWithSections>> getAll();
}
