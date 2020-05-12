package edu.monash.student.happyactive.data.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.InteractivePrompt;
import edu.monash.student.happyactive.data.entities.SessionPhoto;

public class ActivitySessionWithPhotosAndPrompts {
    @Embedded
    public ActivitySession activitySession;

    @Relation(
            parentColumn = "id",
            entityColumn = "sessionId"
    )
    public List<SessionPhoto> sessionPhoto;
    @Relation(
            parentColumn = "id",
            entityColumn = "sessionId"
    )
    public List<InteractivePrompt> interactivePrompts;
    @Relation(
            parentColumn = "activityId",
            entityColumn = "id"
    )
    public ActivityPackage activityPackage;
}
