package com.example.ideator.model.idea;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.room.Upsert;

import com.example.ideator.model.section.Section;

import java.util.List;

@Dao
public interface IdeaDao {

    @Insert
    long insert(Idea idea);

    @Insert
    void insert(List<Section> sections);

    @Update
    void update(Idea idea);

    @Upsert
    void update(List<Section> sections);

    @Delete
    void delete(Idea idea);

    @Transaction
    @Query("Select * FROM idea_table")
    LiveData<List<IdeaWithSections>> getAll();

    @Transaction
    @Query("Select * FROM idea_table WHERE id = :id")
    LiveData<IdeaWithSections> get(long id);
}
