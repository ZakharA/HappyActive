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

/**
 * View Model class for Compare Average Stats Screen.
 */
public class CompareAverageViewModel extends AndroidViewModel {

    private CompareAverageRepository compareAverageRepository;
    private LiveData<List<ActivitySession>> dataForCompletedActivities;

    public CompareAverageViewModel(@NonNull Application application) {
        super(application);
        compareAverageRepository = new CompareAverageRepository(application);
        dataForCompletedActivities = compareAverageRepository.getDataForCompletedActivity();
    }

    /**
     * Method to calculate the average step count and times spent across all activities.
     * @param dataForCompletedActivities
     * @return
     */
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
            Long avgTime = convertMSTimeToHours(totalTime/dataForCompletedActivities.size());
            dataMap.put("AvgSteps", avgSteps);
            dataMap.put("AvgTime", avgTime);
        }
        return dataMap;
    }

    /**
     * Method to convert ms time to hours, minutes and seconds.
     * @param totalTimeMs
     * @return
     */
    public static long convertMSTimeToHours(long totalTimeMs) {
        long seconds = totalTimeMs / 1000;
        long minutes = seconds / 60;
        long hours =  minutes / 60;

        return hours;
    }

    /**
     * Method for fetching activity sessions which are completed.
     * @return
     */
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
