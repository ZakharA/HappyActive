package edu.monash.student.happyactive.ActivityPackages.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivityJournalRepository;
import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivitySessionRepository;
import edu.monash.student.happyactive.data.entities.ActivityJournal;

/**
 *  ViewModel for interacting with journal repository
 */
public class ActivityJournalViewModel extends AndroidViewModel {

    private ActivityJournalRepository activityJournalRepository;
    private ActivityJournal journal;

    public ActivityJournalViewModel(@NonNull Application application, long sessionId) {
        super(application);
        activityJournalRepository = new ActivityJournalRepository(application);
        journal = new ActivityJournal(sessionId, "");
    }

    public void addNewJournalEntry(String journalEntry, Long sessionId) throws ExecutionException, InterruptedException {
        journal.setEntry(journalEntry);
        journal.sessionId = sessionId;
        activityJournalRepository.insertNewEntry(journal);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final long sessionId;

        public Factory(@NonNull Application application, long sessionId){
            this.application = application;
            this.sessionId = sessionId;
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ActivityJournalViewModel(application, sessionId);
        }
    }
}
