package edu.monash.student.happyactive.memoryReel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivitySessionViewModel;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.relationships.ActivitySessionWithPhotos;
import edu.monash.student.happyactive.data.relationships.ActivitySessionWithPhotosAndPrompts;


public class PhotoCollageFragment extends Fragment {


    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CollageAdapter mCollageAdapter;
    private ActivitySessionViewModel mSessionViewModel;
    private String mSelectedMonth;

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
        mSelectedMonth = PhotoCollageFragmentArgs.fromBundle(getArguments()).getSelectedMonth();
        mSessionViewModel = new ViewModelProvider(requireActivity(),
                new ActivitySessionViewModel.Factory(getActivity().getApplication())).get(ActivitySessionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_collage, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.collageMemoryReelContainer);
        recyclerView.setHasFixedSize(true);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("MMM").parse(mSelectedMonth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mSessionViewModel.getSessionWithPhotoAndPromptsBy(String.format("%02d", cal.get(Calendar.MONTH) + 1)
        ).observe(getViewLifecycleOwner(), collageObserver);
        layoutManager = new LinearLayoutManager(getContext());
        return view;
    }

    Observer<List<ActivitySessionWithPhotosAndPrompts>> collageObserver = new Observer<List<ActivitySessionWithPhotosAndPrompts>>() {
        @Override
        public void onChanged(List<ActivitySessionWithPhotosAndPrompts> sessions) {
            mCollageAdapter = new CollageAdapter(sessions);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mCollageAdapter);
        }
    };

}
