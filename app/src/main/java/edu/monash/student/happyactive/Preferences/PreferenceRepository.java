package edu.monash.student.happyactive.Preferences;

import android.app.Application;
import android.os.AsyncTask;

import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.dao.PreferencesDao.PreferencesDao;
import edu.monash.student.happyactive.data.entities.UserPref;

public class PreferenceRepository {
    private PreferencesDao preferencesDao;

    public PreferenceRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        preferencesDao = db.preferencesDao();
    }

    public void updatePreferences(UserPref userPref) {
        new PreferenceRepository.setAsyncTask(preferencesDao).execute(userPref);
    }

    private static class setAsyncTask extends AsyncTask<UserPref, Void, Void> {
        private PreferencesDao mPreferencesDao;

        public setAsyncTask(PreferencesDao mPreferencesDao) {
            this.mPreferencesDao = mPreferencesDao;
        }

        @Override
        protected Void doInBackground(UserPref... userPref) {
            mPreferencesDao.updatePreferences(userPref[0]);
            return null;
        }

    }
}
