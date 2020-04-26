package edu.monash.student.happyactive.Reports.CompareAverage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import edu.monash.student.happyactive.Reports.ReportsRepositories.CompareAverageRepository;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class CompareAverageViewModel extends AndroidViewModel {

    private CompareAverageRepository compareAverageRepository;
    private LiveData<List<ActivitySession>> dataForCompletedActivities;

    public CompareAverageViewModel(@NonNull Application application) {
        super(application);
        compareAverageRepository = new CompareAverageRepository(application);
        dataForCompletedActivities = compareAverageRepository.getDataForCompletedActivity();
    }

    public Map<String, Long> calculateAvgStepCountAndTime(List<ActivitySession> dataForCompletedActivities) {
        Long totalSteps = 0l;
        long totalTime = 0;
        Map<String, Long> dataMap = new HashMap<String, Long>();
        for (ActivitySession activity : dataForCompletedActivities) {
            totalSteps += activity.getStepCount();
            long diffInMillis = Math.abs(activity.getCompletedDateTime().getTime() - activity.getStartDateTime().getTime());
            totalTime += diffInMillis;
        }
        if (totalSteps > 0.0) {
            Long avgSteps = totalSteps/dataForCompletedActivities.size();
            Long avgTime = calculateAverageOfTime(totalTime/dataForCompletedActivities.size());
            dataMap.put("AvgSteps", avgSteps);
            dataMap.put("AvgTime", avgTime);
        }
        return dataMap;
    }

    public static long calculateAverageOfTime(long totalTimeMs) {
        long seconds = totalTimeMs / 1000;
        long minutes = seconds / 60;
        long hours =  minutes / 60;

        return hours;
    }

    public LiveData<List<ActivitySession>> getDataForCompletedActivities() {
        return dataForCompletedActivities;
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
            return (T) new CompareAverageViewModel(application);
        }
    }
}
