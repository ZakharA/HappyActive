package edu.monash.student.happyactive.ActivityPackages.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.ActivityPackageRepository;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.relationships.ActivityPackageWithTasks;

public class ActivityPackageViewModel extends AndroidViewModel {

    private ActivityPackageRepository activityPackageRepository;
    private LiveData<List<ActivityPackage>>  allActivityPackages;

    public ActivityPackageViewModel(@NonNull Application application) {
        super(application);
        activityPackageRepository = new ActivityPackageRepository(application);
        allActivityPackages = activityPackageRepository.getAllActivityPackages();
    }

    public LiveData<List<ActivityPackage>> getAllActivityPackages() {
        return allActivityPackages;
    }
    public LiveData<ActivityPackageWithTasks> getActivityPackageWithTasks(long id) {
        return activityPackageRepository.getActivityWithTasks(id);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;

        public Factory(@NonNull Application application){
            this.application = application;
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ActivityPackageViewModel(application);
        }
    }
}
