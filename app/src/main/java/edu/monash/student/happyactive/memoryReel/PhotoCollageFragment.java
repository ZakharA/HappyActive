package edu.monash.student.happyactive.memoryReel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.relationships.ActivitySessionWithPhotos;
import edu.monash.student.happyactive.fragments.CameraFragmentArgs;


public class PhotoCollageFragment extends Fragment {


    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CollageAdapter mCollageAdapter;

    public PhotoCollageFragment() {
        // Required empty public constructor
    }


    public static PhotoCollageFragment newInstance(String param1, String param2) {
        PhotoCollageFragment fragment = new PhotoCollageFragment();
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
        View view = inflater.inflate(R.layout.fragment_photo_collage, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.collageMemoryReelContainer);
        recyclerView.setHasFixedSize(true);
       // mSessionViewModel.getAllCompletedSessions(ActivityPackageStatus.COMPLETED).observe(getViewLifecycleOwner(), memoryReelObserver);
        layoutManager = new LinearLayoutManager(getContext());
        return view;
    }

    Observer<List<ActivitySessionWithPhotos>> collageObserver = new Observer<List<ActivitySessionWithPhotos>>() {
        @Override
        public void onChanged(List<ActivitySessionWithPhotos> sessions) {
            mCollageAdapter = new CollageAdapter(sessions);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mCollageAdapter);
        }
    };

}
