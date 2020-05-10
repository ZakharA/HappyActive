package edu.monash.student.happyactive.memoryReel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivitySessionViewModel;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivitySession;


public class MemoryReelFragment extends Fragment {

    private ActivitySessionViewModel mSessionViewModel;
    private List<ActivitySession> completedSessions;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SessionAdapter mSessionAdapter;
    private ImageView image;
    private TextView text;

    public MemoryReelFragment() {
    }

    public static MemoryReelFragment newInstance(String param1, String param2) {
        MemoryReelFragment fragment = new MemoryReelFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mSessionViewModel = new ViewModelProvider(requireActivity(),
                new ActivitySessionViewModel.Factory(getActivity().getApplication())).get(ActivitySessionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memory_reel_list, container, false);
        image = (ImageView) view.findViewById(R.id.imageView2);
        text = (TextView) view.findViewById(R.id.textView2);
        recyclerView = (RecyclerView) view.findViewById(R.id.monthRecyclerView);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        mSessionViewModel.getAllCompletedSessions(ActivityPackageStatus.COMPLETED).observe(getViewLifecycleOwner(), memoryReelObserver);
        layoutManager = new LinearLayoutManager(getContext());
        return view;
    }

    Observer<List<ActivitySession>> memoryReelObserver = new Observer<List<ActivitySession>>() {
        @Override
        public void onChanged(List<ActivitySession> sessions) {
            if(sessions.size() > 0){
                image.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            mSessionAdapter = new SessionAdapter(sessions);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mSessionAdapter);
        }
    };


}
