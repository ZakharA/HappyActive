package edu.monash.student.happyactive.Preferences;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.dao.ActivityPackageDao.ActivityPackageDao;
import edu.monash.student.happyactive.data.dao.PreferencesDao.PreferencesDao;
import edu.monash.student.happyactive.data.dao.UserScoreDao.UserScoreDao;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.UserPref;

/**
 * Repository class for Preferences Screen.
 */
public class PreferenceRepository {
    private PreferencesDao preferencesDao;
    private ActivityPackageDao activityPackageDao;
    private UserScoreDao userScoreDao;

    public PreferenceRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        preferencesDao = db.preferencesDao();
        activityPackageDao = db.activityPackageDao();
        userScoreDao = db.userScoreDao();
    }

    /**
     * Async method for updating current user preferences.
     * @param userPref
     */
    public void updatePreferences(UserPref userPref) {
        new PreferenceRepository.updatePrefAsyncTask(preferencesDao, activityPackageDao, userScoreDao).execute(userPref);
    }

    /**
     * Async method for fetching current user preferences.
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public UserPref getPreferences() throws ExecutionException, InterruptedException {
        return new PreferenceRepository.getPreferencesAsyncTask(preferencesDao).execute().get();
    }

    private static class getPreferencesAsyncTask extends AsyncTask<Void, Void, UserPref> {
        private PreferencesDao mPreferencesDao;
        private ActivityPackageDao mActivityPackageDao;
        private UserScoreDao mUserScoreDao;
        private ArrayList<Long> levelScores;

        public getPreferencesAsyncTask(PreferencesDao mPreferencesDao) {
            this.mPreferencesDao = mPreferencesDao;
        }

        @Override
        protected UserPref doInBackground(Void... voids) {
            return mPreferencesDao.getPreferences(1);
        }
    }


    /**
     * Async task for updating user preferences and setting user preferences against
     * the activities
     */
    private static class updatePrefAsyncTask extends AsyncTask<UserPref, Void, Void> {
        private PreferencesDao mPreferencesDao;
        private ActivityPackageDao mActivityPackageDao;
        private UserScoreDao mUserScoreDao;
        private ArrayList<Long> levelScores;

        public updatePrefAsyncTask(PreferencesDao mPreferencesDao, ActivityPackageDao mActivityPackageDao, UserScoreDao mUserScoreDao) {
            this.mPreferencesDao = mPreferencesDao;
            this.mActivityPackageDao = mActivityPackageDao;
            this.mUserScoreDao = mUserScoreDao;
            this.levelScores = new ArrayList<Long>();
            levelScores.add(100l);
            levelScores.add(1000l);
            levelScores.add(5000l);
        }

        @Override
        protected Void doInBackground(UserPref... userPref) {
            // Update preferences
            mPreferencesDao.updatePreferences(userPref[0]);
            // Fetch all activities
            List<ActivityPackage> activityPackages = mActivityPackageDao.getAllActivityPackagesForPref();
            // Fetch current user score for checking level
            long currentUserScore = mUserScoreDao.getCurrentUserScoreForPref(1);
            long userLevel = 0;
            // Finds out users current level
            for (int i = 0; i < levelScores.size(); i++) {
                if (currentUserScore < levelScores.get(i)) {
                    userLevel = i + 1;
                    break;
                }
            }
            // Updates user preference for each activity
            for (ActivityPackage activityPackage : activityPackages) {
                makeRecommendationsForUser(activityPackage, userPref[0], userLevel);
                mActivityPackageDao.updateActivityPackageForPref(activityPackage);
            }
            return null;
        }

        /**
         * Method to update isUserPreferred field in all the activities
         * @param activityPackage
         * @param userPref
         * @param userLevel
         */
        private void makeRecommendationsForUser(ActivityPackage activityPackage, UserPref userPref, long userLevel) {
            // Check for user level against activity level to recommend same level activities
            if (activityPackage.getActivityLevel() == userLevel) {
                /**
                 * Checks according to severity and preferences of the user to assign suitability for activity for user
                 */
                if (userPref.getArthritisCondition() != null && userPref.getArthritisCondition().ordinal() <= activityPackage.getSuitMaxArthritisCondition().ordinal()){
                    activityPackage.setUserPreferred(true);
                }
                else if (userPref.getHobbyList() != null) {
                    String[] hobbyList = userPref.getHobbyList().trim().split(",");
                    for (String hobby : hobbyList) {
                        if (hobby.trim().equals(activityPackage.getType())) {
                            activityPackage.setUserPreferred(true);
                        }
                    }
                }
                else if (userPref.getActivityTime() != null) {
                    if(userPref.getActivityTime().ordinal() == 0 && activityPackage.getApproxTimeToComplete() <= 60) {
                        activityPackage.setUserPreferred(true);
                    }
                    else if (userPref.getActivityTime().ordinal() == 1 && activityPackage.getApproxTimeToComplete() > 60 && activityPackage.getApproxTimeToComplete() <= 120) {
                        activityPackage.setUserPreferred(true);
                    }
                    else if (userPref.getActivityTime().ordinal() == 2 && activityPackage.getApproxTimeToComplete() > 120) {
                        activityPackage.setUserPreferred(true);
                    }
                }
                else if (userPref.getActivityDistance() != null && userPref.getActivityDistance().ordinal() <= activityPackage.getDistance().ordinal() ) {
                    activityPackage.setUserPreferred(true);
                }
                else if (userPref.getGardenAccess() != null && userPref.getGardenAccess() == activityPackage.getGardenAccess()) {
                    activityPackage.setUserPreferred(true);
                }
                else if (userPref.getParkAccess() != null && userPref.getParkAccess() == activityPackage.getParkAccess()) {
                    activityPackage.setUserPreferred(true);
                }
            }

        }

    }
}
