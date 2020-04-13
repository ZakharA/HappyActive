package edu.monash.student.happyactive.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class ActivityWithSessions {
    @Embedded
    public ActivityPackage activityPackage;
    @Relation(
            parentColumn = "id",
            entityColumn = "activityId"
    )
    public List<ActivitySession> sessionList;
}
