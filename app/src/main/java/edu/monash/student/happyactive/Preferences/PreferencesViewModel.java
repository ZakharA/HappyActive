package edu.monash.student.happyactive.Preferences;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import edu.monash.student.happyactive.data.entities.UserPref;

public class PreferencesViewModel extends AndroidViewModel {

    private PreferenceRepository preferenceRepository;

    public PreferencesViewModel(@NonNull Application application) {
        super(application);
        preferenceRepository = new PreferenceRepository(application);
    }

    public void updatePreferences(UserPref userPref) {
        preferenceRepository.updatePreferences(userPref);
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
