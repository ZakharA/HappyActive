package edu.monash.student.happyactive.ActivityPackages;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.relationships.ActivityPackageWithTasks;

public class ActivitySessionViewModel  extends AndroidViewModel {
    private  ActivityPackageRepository activityPackageRepository;
    private LiveData<ActivityPackageWithTasks> acitivtyPackageWithTasks;

    public ActivitySessionViewModel(@NonNull Application application) {
        super(application);
        activityPackageRepository = new ActivityPackageRepository(application);
    }

    public LiveData<ActivityPackageWithTasks> getActivityPackageWithTasks(long id) {
        acitivtyPackageWithTasks = activityPackageRepository.getActivityWithTasks(id);
        return acitivtyPackageWithTasks;
    }
}
