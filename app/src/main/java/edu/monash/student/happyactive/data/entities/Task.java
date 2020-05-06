package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.monash.student.happyactive.data.PromptType;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long activityId;
    public String title;
    public String description;
    public String imagePath;
    public PromptType promptType;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
}



