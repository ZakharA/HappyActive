package edu.monash.student.happyactive.Reports.PostActivityStats;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import edu.monash.student.happyactive.Reports.ReportsRepositories.PostActivityStatsRepository;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class PostActivityStatsViewModel extends AndroidViewModel {

    private PostActivityStatsRepository postActivityStatsRepository;

    public PostActivityStatsViewModel(@NonNull Application application) {
        super(application);
        postActivityStatsRepository = new PostActivityStatsRepository(application);
    }

    public LiveData<ActivitySession> getDataForCurrentSession(Integer currentId) {
        return postActivityStatsRepository.getDataForCurrentSession(currentId);
    }

    public void setStatusCompletedPostActivity(ActivitySession activitySession) {
        postActivityStatsRepository.setStatusCompletedPostActivity(activitySession);
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
            return (T) new PostActivityStatsViewModel(application);
        }
    }
}
