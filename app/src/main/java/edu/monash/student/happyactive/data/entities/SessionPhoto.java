package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SessionPhoto {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long sessionId;
    public String path;
}
