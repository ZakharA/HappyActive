package edu.monash.student.happyactive.Reports.CompareHistory;

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

import edu.monash.student.happyactive.Reports.ReportsRepositories.CompareHistoryRepository;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class CompareHistoryViewModel extends AndroidViewModel {

    private CompareHistoryRepository compareHistoryRepository;
    private LiveData<List<ActivitySession>> dataForCompletedActivities;

    public CompareHistoryViewModel(@NonNull Application application) {
        super(application);
        compareHistoryRepository = new CompareHistoryRepository(application);
        dataForCompletedActivities = compareHistoryRepository.getDataForCompletedActivity();
    }

    public Map<String, String> calculateAvgStepCountAndTime(List<ActivitySession> dataForCompletedActivities) {
        Double totalSteps = 0.0;
        long totalTime = 0;
        Map<String, String> dataMap = new HashMap<String, String>();
        for (ActivitySession activity : dataForCompletedActivities) {
            totalSteps += activity.getStepCount();
            long diffInMillis = Math.abs(activity.getCompletedDateTime().getTime() - activity.getStartDateTime().getTime());
            totalTime += diffInMillis;
        }
        if (totalSteps > 0.0) {
            Double avgSteps = totalSteps/dataForCompletedActivities.size();
            String avgTime = calculateAverageOfTime(totalTime/dataForCompletedActivities.size());
            dataMap.put("AvgSteps", avgSteps.toString()+ " steps");
            dataMap.put("AvgTime", avgTime);
        }
        return dataMap;
    }

    public static String calculateAverageOfTime(Long totalTimeMs) {
        long seconds = totalTimeMs / 1000;
        long minutes = seconds / 60;
        long hours =  minutes / 60;
        minutes = minutes % 60;

        String avgTime = hours + " hours " + minutes + " minutes";

        return avgTime;
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
            return (T) new CompareHistoryViewModel(application);
        }
    }
}
