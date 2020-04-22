package edu.monash.student.happyactive.Reports.PostActivityStats;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import edu.monash.student.happyactive.Reports.ReportsRepositories.PostActivityStatsRepository;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class PostActivityStatsViewModel extends AndroidViewModel {

    private PostActivityStatsRepository postActivityStatsRepository;

    public PostActivityStatsViewModel(@NonNull Application application) {
        super(application);
        postActivityStatsRepository = new PostActivityStatsRepository(application);
    }

    public ActivitySession getDataForCurrentSession(Integer currentId) {
        return postActivityStatsRepository.getDataForCurrentSession(currentId);
    }

    public void setStatusCompletedPostActivity(Integer currentId) {
        postActivityStatsRepository.setStatusCompletedPostActivity(currentId);
    }
}
