package edu.monash.student.happyactive.ActivityPackages.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.monash.student.happyactive.data.ActivityJournalDao;
import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.entities.ActivityJournal;

public class ActivityJournalRepository {
    private ActivityJournalDao activityJournalDao;
    private LiveData<List<ActivityJournal>> activityJournals;

    public ActivityJournalRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        activityJournalDao = db.activityJournalDao();
        activityJournals = activityJournalDao.getAllEntries();
    }

    public long insertNewEntry(ActivityJournal journal) {
        return activityJournalDao.insertNewEntry(journal);
    }

    public ActivityJournal findById(long id) {
        return activityJournalDao.findById(id);
    }
}
