package edu.monash.student.happyactive.ActivityPackages;

import androidx.lifecycle.MutableLiveData;

import java.util.Iterator;
import java.util.List;

import edu.monash.student.happyactive.data.entities.Task;

public class PackageSessionManager {
    public MutableLiveData<Task> currentTaskOnDisplay = new MutableLiveData<>();
    private MutableLiveData<Task> taskInProgress = new MutableLiveData<>();
    private long activityId;
    private List<Task> tasks;
    private Iterator<Task> iterator;

    public PackageSessionManager(long activityId) {
        this.activityId = activityId;
    }

//    public Task getCurrentTaskOnDisplay() {
//        return currentTaskOnDisplay;
//    }
//
//    public Task getTaskInProgress() {
//        return taskInProgress;
//    }

    public long getActivityId() {
        return activityId;
    }

//    public Task completeTaskInProgress() {
//
//    }
//
//    public Task getPreviousTask(){
//        int currentTaskIndex =tasks.indexOf(currentTaskOnDisplay);
//        if ( currentTaskIndex != 0) {
//            currentTaskOnDisplay = tasks.get(currentTaskIndex - 1);
//            return currentTaskOnDisplay;
//        } else {
//            return null; // TODO replace null with something better
//        }
//
//    }
//
//    public Task getTaskAfter(Task currentTaskOnDisplay) {
//        if (!isLastInList(currentTaskOnDisplay) && !isInProgress(currentTaskOnDisplay)){
//            currentTaskOnDisplay = tasks.get(getIndex(currentTaskOnDisplay) + 1);
//            return currentTaskOnDisplay;
//        } else {
//             return taskInProgress;
//        }
//
//    }
//
//    public boolean isInProgress(Task task) {
//        return task == taskInProgress;
//    }
//
//    private boolean isLastInList(Task currentTaskOnDisplay) {
//        return currentTaskOnDisplay.id == getLastTaskId();
//    }

    private int getIndex(Task task) {
        return tasks.indexOf(task);
    }

    private long getLastTaskId() {
        return tasks.get(tasks.size() - 1).id;
    }

    private int getLastTaskIndex() {
        return tasks.size() - 1;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        this.iterator = tasks.iterator();
        this.taskInProgress.setValue(tasks.get(0));
        this.currentTaskOnDisplay.setValue(tasks.get(0));
    }

    public void completeTaskInProgress() {
        if (iterator.hasNext()) {
            Task task = iterator.next();
            taskInProgress.setValue(task);
            currentTaskOnDisplay.setValue(task);
         } else {
            //return getTaskAfter(currentTaskOnDisplay);
        }
    }
}
