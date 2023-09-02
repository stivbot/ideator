package com.example.ideator.model;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class Idea {

    public static String DEFAULT_TITLE = "My concept";

    private String title;
    private String description;
    private List<Section> sections;

    public Idea(String description) {
        this(Idea.DEFAULT_TITLE, description);
    }

    public Idea(String title, String description) {
        this.title = title;
        this.description = description;
        this.sections = new ArrayList<Section>();
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

    public List<Section> getSections() {
        return sections;
    }
}
