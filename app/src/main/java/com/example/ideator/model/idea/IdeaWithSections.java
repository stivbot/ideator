package com.example.ideator.model.idea;

import androidx.room.Embedded;
import androidx.room.Relation;
import androidx.room.Transaction;

import com.example.ideator.model.section.Section;

import java.util.ArrayList;
import java.util.List;

public class IdeaWithSections {
    @Embedded
    public Idea idea;

    @Relation(parentColumn = "id", entityColumn = "idea_id", entity = Section.class)
    public List<Section> sections;

    public IdeaWithSections(Idea idea) {
        this.idea = idea;
        this.sections = new ArrayList<Section>();
    }
}
