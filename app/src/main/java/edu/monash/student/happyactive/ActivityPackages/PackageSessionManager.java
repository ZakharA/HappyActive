package edu.monash.student.happyactive.ActivityPackages;

import java.util.Iterator;
import java.util.List;

import edu.monash.student.happyactive.data.entities.Task;

public class PackageSessionManager {
    private Task currentTaskOnDisplay;
    private Task taskInProgress;
    private long activityId;
    private List<Task> tasks;
    private Iterator<Task> iterator;

    public PackageSessionManager(long activityId, List<Task> tasks) {
        this.activityId = activityId;
        this.tasks = tasks;
        this.iterator = tasks.iterator();
        this.currentTaskOnDisplay = iterator.next();
        this.taskInProgress = currentTaskOnDisplay;
    }

    public Task getCurrentTaskOnDisplay() {
        return currentTaskOnDisplay;
    }

    public Task getTaskInProgress() {
        return taskInProgress;
    }

    public long getActivityId() {
        return activityId;
    }

    public Task completeTaskInProgress() {
        if (iterator.hasNext() && isInProgress(currentTaskOnDisplay)) {
            taskInProgress = iterator.next();
            currentTaskOnDisplay = taskInProgress;
            return taskInProgress;
        } else {
            return getTaskAfter(currentTaskOnDisplay);
        }
    }

    public Task getPreviousTask(){
        int currentTaskIndex =tasks.indexOf(currentTaskOnDisplay);
        if ( currentTaskIndex != 0) {
            currentTaskOnDisplay = tasks.get(currentTaskIndex - 1);
            return currentTaskOnDisplay;
        } else {
            return null; // TODO replace null with something better
        }

    }

    public Task getTaskAfter(Task currentTaskOnDisplay) {
        if (!isLastInList(currentTaskOnDisplay) && !isInProgress(currentTaskOnDisplay)){
            currentTaskOnDisplay = tasks.get(getIndex(currentTaskOnDisplay) + 1);
            return currentTaskOnDisplay;
        } else {
             return taskInProgress;
        }

    }

    private boolean isInProgress(Task task) {
        return task == taskInProgress;
    }

    private boolean isLastInList(Task currentTaskOnDisplay) {
        return currentTaskOnDisplay.id == getLastTaskId();
    }

    private int getIndex(Task task) {
        return tasks.indexOf(task);
    }

    private long getLastTaskId() {
        return tasks.get(tasks.size() - 1).id;
    }

    private int getLastTaskIndex() {
        return tasks.size() - 1;
    }
}
