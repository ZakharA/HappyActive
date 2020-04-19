package edu.monash.student.happyactive.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivitySessionViewModel;
import edu.monash.student.happyactive.R;
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

        mTaskTitle = view.findViewById(R.id.task_title);
        mTaskDescription = view.findViewById(R.id.task_description);
        mProgressBar = view.findViewById(R.id.task_progress_bar);
        Button doneButton = view.findViewById(R.id.done_task_button);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.incrementProgressBy(1);
                updateTaskCard(mSessionViewModel.completeCurrentTask());
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSessionViewModel = new ViewModelProvider(requireActivity(), // get activitypackage id from activity
                new ActivitySessionViewModel.Factory(getActivity().getApplication(), 1l)).get(ActivitySessionViewModel.class);
        mSessionViewModel.getTasksOf(1).observe(getViewLifecycleOwner(), activityPackageWithTasks -> {
            mSessionViewModel.setTaskInSessionManger(activityPackageWithTasks.tasksList);
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
