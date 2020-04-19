package edu.monash.student.happyactive.ActivityPackages.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;
import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.PackageSessionManager;
import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivityPackageRepository;
import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivitySessionRepository;
import edu.monash.student.happyactive.data.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.Task;
import edu.monash.student.happyactive.data.relationships.ActivityPackageWithTasks;

public class ActivitySessionViewModel  extends AndroidViewModel {
    private ActivitySessionRepository activitySessionRepository;
    private ActivityPackageRepository activityPackageRepository;
    private ActivitySession activitySession;
    PackageSessionManager sessionManager;

    public ActivitySessionViewModel(@NonNull Application application, long activityPackageId) {
        super(application);
        activityPackageRepository = new ActivityPackageRepository(application);
        activitySessionRepository = new ActivitySessionRepository(application);
        activitySession = new ActivitySession(activityPackageId, 0, ActivityPackageStatus.STARTED);
        sessionManager = new PackageSessionManager(activityPackageId);
    }

    public void saveSessionAfterActivityIsCompleted(){
        activitySession.status = ActivityPackageStatus.COMPLETED;
        activitySession.completedDateTime = new Date();
        activitySessionRepository.saveSession(activitySession);
    }

    public LiveData<ActivityPackageWithTasks> getTasksOf(long packageId){
        return activityPackageRepository.getActivityWithTasks(packageId);
    }

    public Task completeCurrentTask() {
        return sessionManager.completeTaskInProgress();
    }

    public Task getTaskOnDisplay() {
        return sessionManager.getCurrentTaskOnDisplay();
    }

    public void setTaskInSessionManger(List<Task> tasksList) {
        sessionManager.setTasks(tasksList);
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
