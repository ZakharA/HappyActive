package edu.monash.student.happyactive.ActivityPackages;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivityPackage;

public class ActivityPackageViewModel extends AndroidViewModel {

    private  ActivityPackageRepository activityPackageRepository;
    private LiveData<List<ActivityPackage>>  allActivityPackages;

    public ActivityPackageViewModel(@NonNull Application application) {
        super(application);
        activityPackageRepository = new ActivityPackageRepository(application);
        allActivityPackages = activityPackageRepository.getAllActivityPackages();
    }

    public LiveData<List<ActivityPackage>> getAllActivityPackages() {
        return allActivityPackages;
    }
}
