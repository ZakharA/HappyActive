package edu.monash.student.happyactive.data.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class SessionsWithActivity {
    @Embedded
    public ActivitySession activitySession;
    @Relation(
            parentColumn = "activityId",
            entityColumn = "id",
            entity = ActivityPackage.class
    )
    public ActivityPackage activityPackage;

    public ActivitySession getActivitySession() {
        return activitySession;
    }

    public void setActivitySession(ActivitySession activitySession) {
        this.activitySession = activitySession;
    }

    public ActivityPackage getActivityPackage() {
        return activityPackage;
    }

    public void setActivityPackage(ActivityPackage activityPackage) {
        this.activityPackage = activityPackage;
    }
}
