package edu.monash.student.happyactive.ActivityPackages.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;

import edu.monash.student.happyactive.ActivityPackages.ActivityPackageRepository;
import edu.monash.student.happyactive.ActivityPackages.ActivityPackageStatus;
import edu.monash.student.happyactive.ActivityPackages.PackageSessionManager;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.Task;
import edu.monash.student.happyactive.data.relationships.ActivityPackageWithTasks;

public class ActivitySessionViewModel  extends AndroidViewModel {
    private ActivityPackageRepository activityPackageRepository;
    private LiveData<ActivityPackageWithTasks> acitivtyPackageWithTasks;
    private ActivitySession activitySession;
    private PackageSessionManager sessionManager;

    public ActivitySessionViewModel(@NonNull Application application, ActivityPackage activityPackage) {
        super(application);
        activityPackageRepository = new ActivityPackageRepository(application);
        LiveData<ActivityPackageWithTasks> packageWithTasksLiveData = activityPackageRepository.getActivityWithTasks(activityPackage.id);
        activitySession = new ActivitySession(activityPackage.id, 0, ActivityPackageStatus.STARTED);
        sessionManager = new PackageSessionManager(activityPackage.id, packageWithTasksLiveData.getValue().tasksList);
    }

    public LiveData<ActivityPackageWithTasks> getActivityPackageWithTasks(long id) {
        acitivtyPackageWithTasks = activityPackageRepository.getActivityWithTasks(id);
        return acitivtyPackageWithTasks;
    }

    public Task getTaskToDisplay(){
        return sessionManager.getCurrentTaskOnDisplay();
    }

    public Task completeTaskInProgress(){
        return sessionManager.completeTaskInProgress();
    }

    public Task getPreviousTask(){
        return sessionManager.getPreviousTask();
    }

    public boolean isInProgress() {
        return sessionManager.isInProgress(sessionManager.getCurrentTaskOnDisplay());
    }

    public void saveSessionAfterActivityIsCompleted(){
        activitySession.status = ActivityPackageStatus.COMPLETED;
        activitySession.completedDateTime = new Date();
        activityPackageRepository.saveSession(activitySession);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final ActivityPackage activityPackage;

        public Factory(@NonNull Application application, ActivityPackage activityPackage){
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
