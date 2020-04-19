package edu.monash.student.happyactive.ActivityPackages.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.ActivitySessionDao;
import edu.monash.student.happyactive.data.entities.ActivityJournal;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class ActivitySessionRepository {
    private ActivitySessionDao activitySessionDao;

    public ActivitySessionRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        activitySessionDao = db.activitySessionDao();
    }


    public void updateSession(ActivitySession session) {
         new updateAsyncTask(activitySessionDao).execute(session);
    }

    public void cancelSession(ActivitySession session) {
        activitySessionDao.cancelSession(session);
    }

    public void addNewJournalEntry(ActivityJournal journalEntry) {
        activitySessionDao.addNewJournalEntry(journalEntry);
    }

    public LiveData<List<ActivitySession>> getAllSessions() {
        return activitySessionDao.getAllSessionsRecords();
    }

    public long insertNewSession(ActivitySession session) throws ExecutionException, InterruptedException {
        return new insertAsyncTask(activitySessionDao).execute(session).get();
    }

    public ActivitySession findSessionByActivityId(long activityId, long id) {
        return activitySessionDao.findSessionByActivityId(activityId, id);
    }

    private static class updateAsyncTask extends AsyncTask<ActivitySession, Void, Void> {
        private ActivitySessionDao mSessionDao;

        public updateAsyncTask(ActivitySessionDao mSessionDao) {
            this.mSessionDao = mSessionDao;
        }

        @Override
        protected Void doInBackground(ActivitySession... activitySessions) {
             mSessionDao.updateSession(activitySessions[0]);
             return null;
        }

    }

    private static class insertAsyncTask extends AsyncTask<ActivitySession, Void, Long> {
        private ActivitySessionDao mSessionDao;

        public insertAsyncTask(ActivitySessionDao mSessionDao) {
            this.mSessionDao = mSessionDao;
        }

        @Override
        protected Long doInBackground(ActivitySession... activitySessions) {
            return mSessionDao.insertSession(activitySessions[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }
}
