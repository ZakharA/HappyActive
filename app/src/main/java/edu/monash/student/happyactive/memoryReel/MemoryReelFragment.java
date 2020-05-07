package edu.monash.student.happyactive.memoryReel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivitySessionViewModel;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivitySession;


public class MemoryReelFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ActivitySessionViewModel mSessionViewModel;
    private List<ActivitySession> completedSessions;

    public MemoryReelFragment() {
    }

    public static MemoryReelFragment newInstance(String param1, String param2) {
        MemoryReelFragment fragment = new MemoryReelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mSessionViewModel = new ViewModelProvider(requireActivity(),
                new ActivitySessionViewModel.Factory(getActivity().getApplication())).get(ActivitySessionViewModel.class);
        completedSessions = mSessionViewModel.getAllCompletedSessions(ActivityPackageStatus.COMPLETED).getValue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty_memory_reel, container, false);
    }
}
