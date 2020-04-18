package edu.monash.student.happyactive.ActivityPackages.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.PackageSessionManager;
import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivityPackageRepository;
import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivitySessionRepository;
import edu.monash.student.happyactive.data.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.Task;
import edu.monash.student.happyactive.data.relationships.ActivityPackageWithTasks;

public class ActivitySessionViewModel  extends AndroidViewModel {
    private ActivitySessionRepository activitySessionRepository;
    private ActivityPackageRepository activityPackageRepository;
    private ActivitySession activitySession;

    private final MutableLiveData<Task> currentTaskOnDisplay = new MutableLiveData<>();
    private final MutableLiveData<Task> taskInProgress = new MutableLiveData<>();
    private List<Task> tasks;
    private Iterator<Task> iterator;


    public ActivitySessionViewModel(@NonNull Application application, long activityPackageId) {
        super(application);
        activityPackageRepository = new ActivityPackageRepository(application);
        activitySessionRepository = new ActivitySessionRepository(application);
        activitySession = new ActivitySession(activityPackageId, 0, ActivityPackageStatus.STARTED);
    }

    public void saveSessionAfterActivityIsCompleted(){
        activitySession.status = ActivityPackageStatus.COMPLETED;
        activitySession.completedDateTime = new Date();
        activitySessionRepository.saveSession(activitySession);
    }



//    public Task getCurrentTaskOnDisplay() {
//        return currentTaskOnDisplay;
//    }
//
//    public Task getTaskInProgress() {
//        return taskInProgress;
//    }



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

    public void completeTaskInProgress() {
        if (iterator.hasNext()) {
            final Task task = iterator.next();
            currentTaskOnDisplay.setValue(task);
            //taskInProgress.setValue(task);
        } else {
            //return getTaskAfter(currentTaskOnDisplay);
        }
    }

    public LiveData<Task> getTaskToDisplay() {
        return currentTaskOnDisplay;
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final long activityPackage;

        public Factory(@NonNull Application application, long activityPackage){
            this.application = application;
            this.activityPackage = activityPackage;
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ActivitySessionViewModel(application, activityPackage);
        }
    }
}
