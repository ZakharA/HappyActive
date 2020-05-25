package edu.monash.student.happyactive.Preferences;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.data.entities.UserPref;

/**
 * View Model class for Preferences Screen.
 */
public class PreferencesViewModel extends AndroidViewModel {

    private PreferenceRepository preferenceRepository;

    public PreferencesViewModel(@NonNull Application application) {
        super(application);
        preferenceRepository = new PreferenceRepository(application);
    }

    /**
     * Async method for updating current user preferences.
     * @param userPref
     */
    public void updatePreferences(UserPref userPref) {
        preferenceRepository.updatePreferences(userPref);
    }

    /**
     * Async method for fetching current user preferences.
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public UserPref getPreferences() throws ExecutionException, InterruptedException {
        return preferenceRepository.getPreferences();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;

        public Factory(@NonNull Application application){
            this.application = application;
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PreferencesViewModel(application);
        }
    }
}
