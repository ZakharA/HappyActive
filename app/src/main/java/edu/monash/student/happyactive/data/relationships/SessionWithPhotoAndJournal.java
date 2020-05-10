package edu.monash.student.happyactive.data.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import edu.monash.student.happyactive.data.entities.ActivityJournal;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.SessionPhoto;

public class SessionWithPhotoAndJournal {
    @Embedded
    public ActivitySession activitySession;

    @Relation(
            parentColumn = "id",
            entityColumn = "sessionId"
    )
    public ActivityJournal activityJournal;
    @Relation(
            parentColumn = "id",
            entityColumn = "sessionId"
    )
    public SessionPhoto sessionPhoto;

}
