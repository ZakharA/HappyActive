package edu.monash.student.happyactive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivitySessionViewModel;
import edu.monash.student.happyactive.data.entities.Task;
import edu.monash.student.happyactive.fragments.CameraFragment;


public class SessionFragment extends Fragment {



    private TextView mTaskDescription;
    private TextView mTaskTitle;
    private ActivitySessionViewModel mSessionViewModel;
    private ProgressBar mProgressBar;
    private ImageView mImageView;
    private long activityId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_session, container, false);
        activityId = SessionFragmentArgs.fromBundle(getArguments()).getActivityId();
        MainActivity activity = (MainActivity) requireActivity();
        mTaskTitle = view.findViewById(R.id.task_title);
        mImageView = view.findViewById(R.id.task_image);
        mTaskDescription = view.findViewById(R.id.task_description);
        mProgressBar = view.findViewById(R.id.task_progress_bar);
        Button doneButton = view.findViewById(R.id.done_task_button);
        Button cancelButton = view.findViewById(R.id.cancel_session_button);
        activity.setCheckUpNotification(activityId);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.incrementProgressBy(1);
                mSessionViewModel.updateSteps(activity.getNumberOfSteps());
                if(!mSessionViewModel.isActivityCompleted()) {
                    updateTaskCard(mSessionViewModel.completeCurrentTask());
                } else {
                    doneButton.setText(R.string.complete_activity_text);
                    mSessionViewModel.saveSessionAfterActivityIsCompleted();
                    Navigation.findNavController(view).navigate(
                            SessionFragmentDirections.showJournalFor().setSessionId(mSessionViewModel.getSessionId())
                    );
                }
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
                builder.setTitle(getResources().getString(R.string.dialog_cancel_session))
                        .setNegativeButton(getResources().getText(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mSessionViewModel.updateSteps(activity.getNumberOfSteps());
                                mSessionViewModel.cancelSession();
                                Navigation.findNavController(view).navigate(
                                        SessionFragmentDirections.cancelSession()
                                );
                            }
                        })
                        .show();

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSessionViewModel = new ViewModelProvider(requireActivity(),
                new ActivitySessionViewModel.Factory(getActivity().getApplication(), activityId)).get(ActivitySessionViewModel.class);

        mSessionViewModel.getTasksOf(activityId).observe(getViewLifecycleOwner(), activityPackageWithTasks -> {
            mSessionViewModel.setTaskInSessionManger(activityPackageWithTasks.tasksList);
            try {
                mSessionViewModel.initSession();
            } catch (Exception e) {
                e.printStackTrace();
            }
            setUpProgressBar(activityPackageWithTasks.tasksList);
            updateTaskCard(mSessionViewModel.getTaskOnDisplay());
        });
    }

    private void setUpProgressBar(List<Task> tasksList) {
        mProgressBar.setMax(tasksList.size());
    }

    private void updateTaskCard(Task task){
        mTaskTitle.setText(task.title);
        mTaskDescription.setText(task.description);
        mImageView.setImageResource(getResources()
                .getIdentifier(task.imagePath.split("[.]")[0], "drawable", "edu.monash.student.happyactive"));
    }

}
