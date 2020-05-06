package edu.monash.student.happyactive.data.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivityJournal;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.InteractivePrompt;

public class SessionWithPrompts {
    @Embedded
    public ActivitySession activitySession;

    @Relation(
            parentColumn = "id",
            entityColumn = "sessionId"
    )
    public List<InteractivePrompt> interactivePrompts;

    public SessionWithPrompts(ActivitySession session){
        activitySession = session;
    }
}
