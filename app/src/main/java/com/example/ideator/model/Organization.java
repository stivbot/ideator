package com.example.ideator.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private String name;
    private List<Idea> ideas;
    private Profile profile;

    public Organization(String name) {
        this.name = name;
        this.ideas = new ArrayList<Idea>();
        this.profile = new Profile();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Idea> getIdeas() {
        return ideas;
    }

    public Profile getProfile() {
        return profile;
    }
}
