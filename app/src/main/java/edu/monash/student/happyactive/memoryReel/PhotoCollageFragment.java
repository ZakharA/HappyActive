package edu.monash.student.happyactive.memoryReel;

import android.os.Bundle;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivitySessionViewModel;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.relationships.ActivitySessionWithPhotosAndPrompts;

/**
 * responsible for bootstrapping  collages
 */
public class PhotoCollageFragment extends Fragment {

    private HappyActiveRecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CollageAdapter mCollageAdapter;
    private ActivitySessionViewModel mSessionViewModel;
    private MaterialDatePicker<Pair<Long, Long>> datePicker;

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
        mSessionViewModel = new ViewModelProvider(requireActivity(),
                new ActivitySessionViewModel.Factory(getActivity().getApplication())).get(ActivitySessionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_collage, container, false);
        recyclerView = (HappyActiveRecyclerView) view.findViewById(R.id.collageMemoryReelContainer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(view.findViewById(R.id.emptyActivityLayout));
        datePicker = MaterialDatePicker.Builder.dateRangePicker().build();
        Chip chip = (Chip) view.findViewById(R.id.filterChip);

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Date date = new Date(selection.second);

           mSessionViewModel.getSessionWithPromptsInRange(selection.first, selection.second + 24*60*60*1000).observe(getViewLifecycleOwner(), collageObserver);
        });

        datePicker.addOnNegativeButtonClickListener(selection -> {
            chip.setChecked(false);
        });

        chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    datePicker.show(getParentFragmentManager(), "date");
                } else {
                    mSessionViewModel.getSessionsWithPhotoAndPrompts().observe(getViewLifecycleOwner(), collageObserver);
                }
            }
        });

        mSessionViewModel.getSessionsWithPhotoAndPrompts().observe(getViewLifecycleOwner(), collageObserver);
        layoutManager = new LinearLayoutManager(getContext());
        return view;
    }

    Observer<List<ActivitySessionWithPhotosAndPrompts>> collageObserver = new Observer<List<ActivitySessionWithPhotosAndPrompts>>() {
        @Override
        public void onChanged(List<ActivitySessionWithPhotosAndPrompts> sessions) {
           if(sessions != null || sessions.size() > 1){
               Collections.sort(sessions, (o1, o2) -> {
                   if (o1.activitySession.completedDateTime.after(o2.activitySession.completedDateTime)){
                       return -1;
                   } else if (o1.activitySession.completedDateTime.before(o2.activitySession.completedDateTime)) {
                       return 1;
                   } else {
                       return 0;
                   }
               });
           }
            mCollageAdapter = new CollageAdapter(sessions);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mCollageAdapter);
            mCollageAdapter.notifyDataSetChanged();
        }
    };
}
