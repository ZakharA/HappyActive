package edu.monash.student.happyactive.ActivityPackages.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.dao.ActivityPackageDao.ActivityPhotoDao;
import edu.monash.student.happyactive.data.entities.SessionPhoto;

/**
 * This class serves as an intermediate between ROOM and LiveModels
 * and responsible form executing request in the background
 */
public class ActivityPhotoRepository {

    ActivityPhotoDao activityPhotoDao;
    
    public ActivityPhotoRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        activityPhotoDao = db.activityPhotoDao();
    }

    public long saveSessionPhoto(SessionPhoto sessionPhoto) throws ExecutionException, InterruptedException {
        return new saveAsyncTask(activityPhotoDao).execute(sessionPhoto).get();
    }

    private static class saveAsyncTask extends AsyncTask<SessionPhoto, Void, Long> {
        private ActivityPhotoDao activityPhotoDao;

        public saveAsyncTask(ActivityPhotoDao mSessionDao) {
            this.activityPhotoDao = mSessionDao;
        }

        @Override
        protected Long doInBackground(SessionPhoto... sessionPhotos) {
            return activityPhotoDao.saveSessionPhoto(sessionPhotos[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }
}
