package edu.monash.student.happyactive.Reports.CompareAverage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.Reports.ReportsRepositories.CompareAverageRepository;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.UserPref;
import edu.monash.student.happyactive.data.enumerations.UserAge;
import edu.monash.student.happyactive.data.enumerations.UserGender;

/**
 * View Model class for Compare Average Stats Screen.
 */
public class CompareAverageViewModel extends AndroidViewModel {

    private CompareAverageRepository compareAverageRepository;
    private static final String STEPS_AGE_GENDER = "statisticsFiles/steps_by_age_gender_20170508.csv";

    public CompareAverageViewModel(@NonNull Application application) {
        super(application);
        compareAverageRepository = new CompareAverageRepository(application);
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
     * Method to process user age and gender to find suitable suggestions.
     * @param activity
     * @return [step count aus, ]
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public ArrayList<String> processStepsByAgeFile(FragmentActivity activity) throws ExecutionException, InterruptedException {
        // Fetches user preferences
        UserPref userPref = compareAverageRepository.fetchUserAgeGender();
        // Extracts user age and gender from preferences
        UserGender userGender = userPref != null ? userPref.getUserGender() : null;
        UserAge userAge = userPref != null ? userPref.getUserAge() : null;
        String[] record = null;
        ArrayList<String> result = new ArrayList<String>();
        try {
            InputStream inputStream = activity.getAssets().open(STEPS_AGE_GENDER);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            /**
             * Reads each line of data file and processes users wear time and avg step counts
             * based on users age and gender. If nothing specified defaults to Male 70+ stats.
             */
            while((line = br.readLine()) != null) {
                record = line.split(",");
                if (userAge != null && userGender != null && userGender != UserGender.OTHER) {
                    if (userAge == UserAge.SIXTY_SEVENTY &&
                            record[1].replace("\"", "").startsWith("[60") &&
                            record[0].replace("\"", "").equalsIgnoreCase(userGender.getValue()) ) {
                        result.add(record[3]);
                        result.add("Avg Steps/Day 60-70 " + userGender.getValue().toLowerCase());
                        break;
                    }
                    else if (userAge == UserAge.SEVENTY_PLUS &&
                            record[1].replace("\"", "").startsWith("[70") &&
                            record[0].replace("\"", "").equalsIgnoreCase(userGender.getValue()) ){
                        result.add(record[3]);
                        result.add("Avg Steps/Day 70+ " + userGender.getValue().toLowerCase());
                        break;
                    }
                }
                else {
                    if (record[1].contains("[70")) {
                        result.add(record[3]);
                        result.add("Avg Steps/Day 70+ male");
                        break;
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Method for fetching activity sessions which are completed.
     * @return
     */
    public LiveData<List<ActivitySession>> getDataForCompletedActivities() {
        return compareAverageRepository.getDataForCompletedActivity();
    }

    /**
     * Method for fetching activity sessions which are completed for charts.
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<ActivitySession> getDataForCompletedActivitiesForChart() throws ExecutionException, InterruptedException {
        return compareAverageRepository.getDataForCompletedActivitiesForChart();
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
