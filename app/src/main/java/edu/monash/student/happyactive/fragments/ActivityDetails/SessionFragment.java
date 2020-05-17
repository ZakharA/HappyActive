package edu.monash.student.happyactive.fragments.ActivityDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.PromptFragment;
import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivitySessionViewModel;
import edu.monash.student.happyactive.MainActivity;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.enumerations.PromptType;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.Task;
import jp.wasabeef.glide.transformations.BlurTransformation;


public class SessionFragment extends Fragment {

    private TextView mTaskDescription;
    private TextView mTaskTitle;
    private ActivitySessionViewModel mSessionViewModel;
    private ProgressBar mProgressBar;
    private ImageView mImageView;
    private long activityId;
    private TextView mTaskCompleteView;
    private Button doneButton;
    private Button previousButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_session, container, false);

        MainActivity activity = (MainActivity) requireActivity();

        activityId = SessionFragmentArgs.fromBundle(getArguments()).getActivityId();
        mTaskTitle = view.findViewById(R.id.task_title);
        mImageView = view.findViewById(R.id.task_image);
        mTaskCompleteView = view.findViewById(R.id.task_completed_label);
        mTaskDescription = view.findViewById(R.id.task_description_session);
        mProgressBar = view.findViewById(R.id.task_progress_bar);
        doneButton = view.findViewById(R.id.done_task_button);
        previousButton = view.findViewById(R.id.previous_task_button);
        Button cancelButton = view.findViewById(R.id.cancel_session_button);
        activity.setCheckUpNotification(activityId);

        doneButton.setOnClickListener(new View.OnClickListener() {
            /**
             *   completes current task on the screen and show next one or finish the session
             *   the package is completed.
             *   update progress bar every time the user finishes with a task
              * @param v view of the fragment
             */
            @Override
            public void onClick(View v) {
                if(mSessionViewModel.isPreviousTaskOnDisplay()) {
                    Task taskToDisplay = mSessionViewModel.getTaskAfter(mSessionViewModel.getTaskOnDisplay());
                    mSessionViewModel.setTaskOnDisplay(taskToDisplay);
                    updateTaskCard(taskToDisplay);
                    previousButton.setEnabled(true);
                } else {
                    mProgressBar.incrementProgressBy(1);
                    mSessionViewModel.updateSteps(activity.getNumberOfSteps());
                    previousButton.setEnabled(true);
                    if(!mSessionViewModel.isActivityCompleted()) {
                        updateTaskCard(mSessionViewModel.completeCurrentTask());
                        if (mSessionViewModel.isActivityCompleted())
                            doneButton.setText(R.string.complete_activity_text);
                    } else {
                        mSessionViewModel.saveSessionAfterActivityIsCompleted();
                        activity.cancelCheckUpNotification();
                        Navigation.findNavController(view).navigate(
                                SessionFragmentDirections.showJournalFor().setSessionId(mSessionViewModel.getSessionId())
                        );
                    }
                }

            }
        });

        previousButton.setEnabled(false);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = mSessionViewModel.getPreviousTasK();
                updateTaskCard(task);
                if(mSessionViewModel.isFirstTask(task)) {
                    previousButton.setEnabled(false);
                }
            }
        });

        /**
         * cancels a current session and navigates to the main screen
         */
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
                                activity.cancelCheckUpNotification();
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

    /**
     * gets the session view model after the fragment is rendered
     * and initialize a new activity session in the view model.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSessionViewModel = new ViewModelProvider(requireActivity(),
                new ActivitySessionViewModel.Factory(getActivity().getApplication(), activityId)).get(ActivitySessionViewModel.class);

        mSessionViewModel.getTasksOf(activityId).observe(getViewLifecycleOwner(), activityPackageWithTasks -> {
            ActivitySession activitySession = null;
            try {
                activitySession = mSessionViewModel.checkInProgress(activityId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // In case of in progress activity
            if (activitySession != null) {
                mSessionViewModel.setTaskInSessionManger(activityPackageWithTasks.tasksList, activitySession.getCurrentTaskId());
                mSessionViewModel.setActivitySession(activitySession);
                for (Task task : activityPackageWithTasks.tasksList) {
                    if (task.getId() == activitySession.getCurrentTaskId()) {
                        break;
                    }
                    mProgressBar.incrementProgressBy(1);
                }
                if (mSessionViewModel.isActivityCompleted()) {
                    doneButton.setText(R.string.complete_activity_text);
                }
            }
            // In case of recommended/all activities
            else {
                mSessionViewModel.setTaskInSessionManger(activityPackageWithTasks.tasksList);
                try {
                    mSessionViewModel.initSession(activityId, mSessionViewModel.getTaskOnDisplay().getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            setUpProgressBar(activityPackageWithTasks.tasksList);
            updateTaskCard(mSessionViewModel.getTaskOnDisplay());
        });
    }

    /**
     * sets the limit for the max number of increments in the progressbar
     * @param tasksList
     */
    private void setUpProgressBar(List<Task> tasksList) {
        updateProgressTextView(tasksList.size());
        mProgressBar.setMax(tasksList.size());
    }

    /**
     * updates the view elements with a new task information
     * @param task
     */
    private void updateTaskCard(Task task){
        mTaskTitle.setText(task.getTitle());
        mTaskDescription.setText(task.getDescription());

        Glide.with(this)
                .load(getResources().getIdentifier(task.imagePath.split("[.]")[0], "drawable", "edu.monash.student.happyactive"))
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(3, 2)))
                .into(mImageView);
        if(task.promptType != PromptType.NONE && (getChildFragmentManager().findFragmentByTag("prompt") == null)) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view_prompt, PromptFragment.newInstance(activityId, mSessionViewModel.getTaskOnDisplay().promptType), "prompt")
                    .commit();
        } else {
            if(getChildFragmentManager().findFragmentByTag("prompt") != null) {
                getChildFragmentManager().beginTransaction()
                        .remove(getChildFragmentManager().findFragmentByTag("prompt"))
                        .commit();
            }
        }
    }

    private void updateProgressTextView(int numberOfTasks){
        mTaskCompleteView.setText("Total number of tasks: " + numberOfTasks );
    }


}