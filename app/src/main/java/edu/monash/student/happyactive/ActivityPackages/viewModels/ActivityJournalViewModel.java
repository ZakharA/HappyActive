package edu.monash.student.happyactive.ActivityPackages.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import edu.monash.student.happyactive.ActivityPackages.ActivityPackageRepository;
import edu.monash.student.happyactive.data.entities.ActivityJournal;

public class ActivityJournalViewModel extends AndroidViewModel {
    private ActivityPackageRepository activityPackageRepository;

    public ActivityJournalViewModel(@NonNull Application application) {
        super(application);
        activityPackageRepository = new ActivityPackageRepository(application);
    }

    public void addNewJournalEntry(ActivityJournal journalEntry) {
        activityPackageRepository.addNewJournalEntry(journalEntry);
    }
}
