package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class ActivitySession {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long activityId;
    public long currentTaskId;
    public String status;
    public int stepCount;
    public Date StartDateTime;
    public Date CompletedDateTime;
}
