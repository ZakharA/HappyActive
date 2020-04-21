package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ActivityPackage {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;
    public String description;

    public ActivityPackage(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
