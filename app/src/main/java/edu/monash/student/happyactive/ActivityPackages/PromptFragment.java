package edu.monash.student.happyactive.ActivityPackages;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivitySessionViewModel;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.enumerations.PromptType;
import edu.monash.student.happyactive.data.entities.InteractivePrompt;

import static android.app.Activity.RESULT_OK;


public class PromptFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private ActivitySessionViewModel mSessionViewModel;
    private long mActivityId;
    private TextInputEditText mTextPrompt;
    private InteractivePrompt mInteractivePrompt;
    private FloatingActionButton mPhotoButton;
    private PromptType mPromptType;
    private String currentPhotoPath;
    private Uri photoURI;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public PromptFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PromptFragment newInstance(long activityId, PromptType promptType) {
        PromptFragment fragment = new PromptFragment();
        Bundle args = new Bundle();
        args.putLong("activityId", activityId);
        args.putSerializable("promptType", promptType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInteractivePrompt = new InteractivePrompt();
        if (getArguments() != null) {
            mActivityId = getArguments().getLong("activityId");
            mPromptType = (PromptType) getArguments().getSerializable("promptType");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        switch (mPromptType){
            case TEXT:
                view = inflater.inflate(R.layout.fragment_text_prompt, container, false);
                mTextPrompt = (TextInputEditText) view.findViewById(R.id.textPrompt);
                mTextPrompt.addTextChangedListener(this);
                break;
            case PHOTO:
                view = inflater.inflate(R.layout.fragment_camera_prompt, container, false);
                mPhotoButton = (FloatingActionButton) view.findViewById(R.id.camera_button);
                mPhotoButton.setOnClickListener(this);
                break;

        }
        return view;
    }

    private boolean taskExistsIn(List<InteractivePrompt> promptList, long id) {
        boolean result = false;
        for(InteractivePrompt prompt: promptList){
             result = prompt.taskId == id ?  true :  false;
        }
        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSessionViewModel = new ViewModelProvider(requireActivity(),
                new ActivitySessionViewModel.Factory(getActivity().getApplication(), mActivityId)).get(ActivitySessionViewModel.class);
    }

    @Override
    public void onClick(View v) {
        dispatchTakePictureIntent();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        List<InteractivePrompt> promptList = mSessionViewModel.getSessionWithPrompts();
        if(!taskExistsIn(promptList, mSessionViewModel.getTaskOnDisplay().id)) {
            mInteractivePrompt.sessionId = mSessionViewModel.getSessionId();
            mInteractivePrompt.taskId = mSessionViewModel.getTaskOnDisplay().id;
            mInteractivePrompt.answer = s.toString();
            mSessionViewModel.getSessionWithPrompts().add(mInteractivePrompt);
        } else {
            promptList.get( promptList.indexOf(mInteractivePrompt)).answer = s.toString();
        }
    }

    /**
     * creates an image in the app folder
     * for the camera to save it in that file
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PhotoPrompt" + timeStamp + "";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * dispatches the intent to open a camera and safe the picture in the file
     */
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
            List<InteractivePrompt> promptList = mSessionViewModel.getSessionWithPrompts();
            if(!taskExistsIn(promptList, mSessionViewModel.getTaskOnDisplay().id)) {
                mInteractivePrompt.sessionId = mSessionViewModel.getSessionId();
                mInteractivePrompt.taskId = mSessionViewModel.getTaskOnDisplay().id;
                mInteractivePrompt.answer = photoURI.getPath();
                mSessionViewModel.getSessionWithPrompts().add(mInteractivePrompt);
            }
        }
    }
}
