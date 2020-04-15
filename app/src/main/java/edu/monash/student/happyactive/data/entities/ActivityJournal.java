package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ActivityJournal {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long sessionId;
    public String entry;
}
