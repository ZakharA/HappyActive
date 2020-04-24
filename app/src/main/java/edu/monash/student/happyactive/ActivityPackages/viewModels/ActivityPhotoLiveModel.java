package edu.monash.student.happyactive.ActivityPackages.viewModels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivityPhotoRepository;
import edu.monash.student.happyactive.data.entities.SessionPhoto;

public class ActivityPhotoLiveModel extends AndroidViewModel {

    private ActivityPhotoRepository activityPhotoRepository;
    private SessionPhoto sessionPhoto;



    public ActivityPhotoLiveModel(@NonNull Application application, long sessionId) {
        super(application);
        activityPhotoRepository = new ActivityPhotoRepository(application);
        sessionPhoto = new SessionPhoto();
        sessionPhoto.setSessionId(sessionId);
    }

    public void savePhoto(Uri photoUrl) throws ExecutionException, InterruptedException {
        sessionPhoto.setPath(photoUrl.getLastPathSegment());
        activityPhotoRepository.saveSessionPhoto(sessionPhoto);
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
            return (T) new ActivityPhotoLiveModel(application, sessionId);
        }
    }
}
