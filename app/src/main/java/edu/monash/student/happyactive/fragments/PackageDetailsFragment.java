package edu.monash.student.happyactive.fragments;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.TaskInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivityPackageViewModel;
import edu.monash.student.happyactive.PackageDetails;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.SessionActivity;
import edu.monash.student.happyactive.data.entities.ActivityPackage;

public class PackageDetailsFragment extends Fragment {

    public static final String ACTIVITY_ID = "activity_id";
    private long selectedActivityId;
    private ActivityPackageViewModel mPackageViewModel;


    public static PackageDetailsFragment newInstance() {
        return new PackageDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            selectedActivityId = bundle.getLong(ACTIVITY_ID, 0l);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.package_details_fragment, container, false);
        Button startButton = view.findViewById(R.id.start_activity_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SessionActivity.class);
                intent.putExtra(SessionActivity.ACTIVITY_ID, selectedActivityId);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPackageViewModel =  new ViewModelProvider(requireActivity(),
                new ActivityPackageViewModel.Factory(getActivity().getApplication())).get(ActivityPackageViewModel.class);

        mPackageViewModel.getActivityPackageById(selectedActivityId).observe(getViewLifecycleOwner(), activity -> {
            ((TextView) view.findViewById(R.id.activity_title)).setText(activity.title);
            ((TextView) view.findViewById(R.id.activity_description)).setText(activity.description);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
