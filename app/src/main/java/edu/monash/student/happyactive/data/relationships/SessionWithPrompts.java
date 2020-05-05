package edu.monash.student.happyactive.data.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import edu.monash.student.happyactive.data.entities.ActivityJournal;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class SessionWithPrompts {
    @Embedded
    public ActivitySession activitySession;

    @Relation(
            parentColumn = "id",
            entityColumn = "sessionId"
    )
    public ActivityJournal activityJournal;
}
