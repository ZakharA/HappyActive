package edu.monash.student.happyactive.ActivityPackages.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivityPackageRepository;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.relationships.ActivityPackageWithTasks;

public class ActivityPackageViewModel extends AndroidViewModel {

    private ActivityPackageRepository activityPackageRepository;
    private LiveData<List<ActivityPackage>>  allActivityPackages;
    private MutableLiveData<ActivityPackage> selectedPackage = new MutableLiveData<ActivityPackage>();
    private  LiveData<PagedList<ActivityPackage>>  activityPackagesPages;

    public ActivityPackageViewModel(@NonNull Application application) {
        super(application);
        activityPackageRepository = new ActivityPackageRepository(application);
        allActivityPackages = activityPackageRepository.getAllActivityPackages();
    }

    PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .setPageSize(20).build();

    public LiveData<PagedList<ActivityPackage>> getActivityPackagesPages(){
        activityPackagesPages = new LivePagedListBuilder<>(activityPackageRepository.getActivityPackagesAsList(),
                pagedListConfig).build();
        return activityPackagesPages;
    }

    public LiveData<List<ActivityPackage>> getAllActivityPackages() {
        return allActivityPackages;
    }

    public LiveData<ActivityPackage> getActivityPackageById(long argItemId) {
        return  activityPackageRepository.getActivityPackageById(argItemId);
    }

    public void setSelectedActivityPackage(ActivityPackage activityPackage){
        selectedPackage.setValue(activityPackage);
    }

    public LiveData<ActivityPackage> getSelectedPackage() {
        return selectedPackage;
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
