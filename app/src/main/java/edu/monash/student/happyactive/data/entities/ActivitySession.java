package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import edu.monash.student.happyactive.data.enumerations.ActivityPackageStatus;

@Entity
public class ActivitySession {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long activityId;
    public long currentTaskId;
    public ActivityPackageStatus status;
    public int stepCount;
    public Date startDateTime;
    public Date completedDateTime;

    public ActivitySession(long activityId, long currentTaskId, ActivityPackageStatus status) {
        this.activityId = activityId;
        this.currentTaskId = currentTaskId;
        this.status = status;
        this.startDateTime = new Date();
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public long getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(long currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public ActivityPackageStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityPackageStatus status) {
        this.status = status;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getCompletedDateTime() {
        return completedDateTime;
    }

    public void setCompletedDateTime(Date completedDateTime) {
        this.completedDateTime = completedDateTime;
    }
}
