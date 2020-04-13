package edu.monash.student.happyactive.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import edu.monash.student.happyactive.data.entities.ActivityJournal;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class ActivitySessionWithJournal {
    @Embedded
    public ActivitySession activitySession;

    @Relation(
            parentColumn = "id",
            entityColumn = "sessionId"
    )
    public ActivityJournal activityJournal;
}
