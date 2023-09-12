package com.example.ideator.model.idea;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "idea_table")
public class Idea {

    public static String DEFAULT_TITLE = "My concept";

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String description;

    @Ignore
    public Idea(String description) {
        this(Idea.DEFAULT_TITLE, description);
    }

    public Idea(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
