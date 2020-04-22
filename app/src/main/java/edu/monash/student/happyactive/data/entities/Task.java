package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long activityId;
    public String title;
    public String description;
    public String imagePath;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
}



