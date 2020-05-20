package edu.monash.student.happyactive.ActivityPackages.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.data.dao.ActivityPackageDao.ActivityJournalDao;
import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.entities.ActivityJournal;

/**
 * This class serves as an intermediate between ROOM and LiveModels
 * and responsible form executing request in the background
 */
public class ActivityJournalRepository {
    private ActivityJournalDao activityJournalDao;
    private LiveData<List<ActivityJournal>> activityJournals;

    public ActivityJournalRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        activityJournalDao = db.activityJournalDao();
        activityJournals = activityJournalDao.getAllEntries();
    }

    public long insertNewEntry(ActivityJournal journal) throws ExecutionException, InterruptedException {
        return new insertAsyncTask(activityJournalDao).execute(journal).get();
    }

    public ActivityJournal findById(long id) {
        return activityJournalDao.findById(id);
    }

    private static class insertAsyncTask extends AsyncTask<ActivityJournal, Void, Long> {
        private ActivityJournalDao activityJournalDao;

        public insertAsyncTask(ActivityJournalDao activityJournalDao) {
            this.activityJournalDao = activityJournalDao;
        }

        @Override
        protected Long doInBackground(ActivityJournal... activityJournals) {
            return activityJournalDao.insertNewEntry(activityJournals[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }
}
