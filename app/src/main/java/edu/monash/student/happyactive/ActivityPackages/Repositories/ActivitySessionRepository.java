package edu.monash.student.happyactive.ActivityPackages.Repositories;

import android.app.Application;

import java.util.List;

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

    public void saveSession(ActivitySession session) {
        activitySessionDao.insertSession(session);
    }

    public void updateSession(ActivitySession session) {
        activitySessionDao.updateSession(session);
    }

    public void cancelSession(ActivitySession session) {
        activitySessionDao.cancelSession(session);
    }

    public void addNewJournalEntry(ActivityJournal journalEntry) {
        activitySessionDao.addNewJournalEntry(journalEntry);
    }
}
