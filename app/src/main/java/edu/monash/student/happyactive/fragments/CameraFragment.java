package edu.monash.student.happyactive.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivityJournalViewModel;
import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivityPhotoLiveModel;
import edu.monash.student.happyactive.R;

import static android.app.Activity.RESULT_OK;


public class CameraFragment extends Fragment {
    public static final String SESSION_ID = "sessionId" ;
    String currentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mPhotoImageView;
    private Uri photoURI;
    private FloatingActionButton photoButton;
    private ActivityPhotoLiveModel mActivityPhotoLiveModel;
    private ActivityJournalViewModel mActivityJournalViewModel;

    private long sessionId;
    private TextInputLayout feelingsTextView;
    private View view;

    public CameraFragment() { }

    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionId = CameraFragmentArgs.fromBundle(getArguments()).getSessionId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_camera, container, false);

        mPhotoImageView = view.findViewById(R.id.session_photo);
        photoButton = view.findViewById(R.id.camera_button);
        feelingsTextView = view.findViewById(R.id.feelingTextField);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivityPhotoLiveModel = new ViewModelProvider(requireActivity(),
                new ActivityPhotoLiveModel.Factory(getActivity().getApplication(), sessionId)).get(ActivityPhotoLiveModel.class);
        mActivityJournalViewModel = new ViewModelProvider(requireActivity(),
                new ActivityJournalViewModel.Factory(getActivity().getApplication(), sessionId)).get(ActivityJournalViewModel.class);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "SessionID" + timeStamp + "";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("File", "dispatchTakePictureIntent: ", ex.fillInStackTrace());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getContext(),
                        "edu.monash.student.happyactive.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);


                List<ResolveInfo> resInfoList = getContext().getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    getContext().grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoURI);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mPhotoImageView.setImageBitmap(bitmap);
            try {
                buttonTransition();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void buttonTransition() throws ExecutionException, InterruptedException {
        mActivityPhotoLiveModel.savePhoto(photoURI);
        String text = feelingsTextView.getEditText().getText().toString();
        if(!text.isEmpty()) {
            mActivityJournalViewModel.addNewJournalEntry(text);
        }
        Navigation.findNavController(view).navigate(
                CameraFragmentDirections.showPostActivityStats().setSessionId(sessionId)
        );
    }
}
