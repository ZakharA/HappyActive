package edu.monash.student.happyactive.ActivityPackages.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivityPackageRepository;
import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivitySessionRepository;
import edu.monash.student.happyactive.data.entities.ActivityJournal;

public class ActivityJournalViewModel extends AndroidViewModel {

    private ActivitySessionRepository activitySessionRepository;

    public ActivityJournalViewModel(@NonNull Application application) {
        super(application);
        activitySessionRepository = new ActivitySessionRepository(application);
    }

    public void addNewJournalEntry(ActivityJournal journalEntry) {
        activitySessionRepository.addNewJournalEntry(journalEntry);
    }
}
