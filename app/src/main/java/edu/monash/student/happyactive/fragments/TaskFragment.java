package edu.monash.student.happyactive.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivitySessionViewModel;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.SessionActivity;
import edu.monash.student.happyactive.data.entities.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    private TextView mTaskDescription;
    private TextView mTaskTitle;
    private ActivitySessionViewModel mSessionViewModel;
    private ProgressBar mProgressBar;
    private long activityId;
    private SessionActivity activity;

    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_task, container, false);

        activity = (SessionActivity) getActivity();
        activityId = activity.getActivityId();

        mTaskTitle = view.findViewById(R.id.task_title);
        mTaskDescription = view.findViewById(R.id.task_description);
        mProgressBar = view.findViewById(R.id.task_progress_bar);
        Button doneButton = view.findViewById(R.id.done_task_button);
        Button cancelButton = view.findViewById(R.id.cancel_session_button);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.incrementProgressBy(1);
                if(!mSessionViewModel.isActivityCompleted()) {
                    updateTaskCard(mSessionViewModel.completeCurrentTask());
                } else {
                    doneButton.setText(R.string.complete_activity_text);
                    mSessionViewModel.saveSessionAfterActivityIsCompleted();
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
                                mSessionViewModel.cancelSession();
                                //#TODO redirect to main screen
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
    }
}
