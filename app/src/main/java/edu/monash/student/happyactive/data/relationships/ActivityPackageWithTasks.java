package edu.monash.student.happyactive.data.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.Task;

public class ActivityPackageWithTasks {
    @Embedded public ActivityPackage activityPackage;
    @Relation(
            parentColumn = "id",
            entityColumn = "activityId"
    )
    public List<Task> tasksList;
}
