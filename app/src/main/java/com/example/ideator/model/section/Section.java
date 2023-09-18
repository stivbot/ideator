package com.example.ideator.model.section;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.ideator.model.idea.Idea;

@Entity(tableName = "section_table")
public class Section {

    public static String TITLE_PROBLEMATIC = "Problematic";
    public static String TITLE_SOLUTION = "Solution";
    public static String TITLE_PROS = "Pros";
    public static String TITLE_CONS = "Cons";

    public static Section createProblematic(String description) {
        return new Section(TITLE_PROBLEMATIC, description);
    }

    public static Section createSolution(String description) {
        return new Section(TITLE_SOLUTION, description);
    }

    public static Section createPros(String description) {
        return new Section(TITLE_PROS, description);
    }

    public static Section createCons(String description) {
        return new Section(TITLE_CONS, description);
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    @ColumnInfo(name = "idea_id")
    private long ideaId;

    public Section(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(long ideaId) {
        this.ideaId = ideaId;
    }
}
