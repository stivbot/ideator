package com.example.ideator.model.organization;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.ideator.model.profile.Profile;
import com.example.ideator.model.idea.Idea;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "organization_table")
public class Organization {
    @PrimaryKey
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
