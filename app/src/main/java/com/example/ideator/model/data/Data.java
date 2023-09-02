package com.example.ideator.model.data;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ideator.model.idea.Idea;
import com.example.ideator.model.organization.Organization;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Data implements Serializable {

    public static String SHARED_PREFERENCES_KEY = "local_data.v1";
    public static Data DEFAULT_DATA = new Data();
    static {
        Organization example = new Organization("Example organization");
        example.getIdeas().add(new Idea("My first idea", "This is short description."));
        DEFAULT_DATA.getOrganizations().add(example);
    }

    private static Data instance;

    public static Data getInstance(AppCompatActivity activity) {
        if(instance == null) {
            instance = Data.get(activity);
            instance.save(activity);
        }
        return instance;
    }

    private static Data get(AppCompatActivity activity) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
        String json = sharedPreferences.getString(SHARED_PREFERENCES_KEY, Data.DEFAULT_DATA.toJson());
        return gson.fromJson(json, Data.class);
    }

    public void save(AppCompatActivity activity) {
        SharedPreferences sharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_KEY, this.toJson());
        editor.commit();
    }

    private List<Organization> organizations;

    private Data() {
        this.organizations = new ArrayList<Organization>();
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
