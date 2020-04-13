package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class ActivityPackage {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;
    public String description;
}
