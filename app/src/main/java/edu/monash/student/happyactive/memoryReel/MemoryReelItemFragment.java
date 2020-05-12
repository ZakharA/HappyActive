package edu.monash.student.happyactive.memoryReel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivitySessionViewModel;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.relationships.ActivitySessionWithPhotos;
import edu.monash.student.happyactive.data.relationships.SessionWithPhotoAndJournal;


public class MemoryReelItemFragment extends Fragment {


    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ActivitySessionViewModel mSessionViewModel;
    private ReelItemAdapter mItemAdapter;
    private TextView titleView;
    private ImageView imageView;

    public MemoryReelItemFragment() {
        // Required empty public constructor
    }

    public static MemoryReelItemFragment newInstance(String param1, String param2) {
        MemoryReelItemFragment fragment = new MemoryReelItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mSelectedMonth = PhotoCollageFragmentArgs.fromBundle(getArguments()).getSelectedMonth();
        mSessionViewModel = new ViewModelProvider(requireActivity(),
                new ActivitySessionViewModel.Factory(getActivity().getApplication())).get(ActivitySessionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memory_reel_item, container, false);
        titleView = (TextView) view.findViewById(R.id.reel_session_activity);
        imageView = (ImageView) view.findViewById(R.id.reel_session_image);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_rv_reel_item);
        recyclerView.setHasFixedSize(true);
        long sessionId = MemoryReelItemFragmentArgs.fromBundle(getArguments()).getSessionId();
        mSessionViewModel.getSessionWIthPhotoAndJournalBy(sessionId).observe(getViewLifecycleOwner(), itemObserver);
        layoutManager = new LinearLayoutManager(getContext());
        return view;
    }

    Observer<SessionWithPhotoAndJournal> itemObserver = new Observer<SessionWithPhotoAndJournal>() {

        @Override
        public void onChanged(SessionWithPhotoAndJournal sessions) {
            titleView.setText(sessions.activityJournal.entry);

            if(sessions.sessionPhoto != null){
                String photoPath = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) +"/" + sessions.sessionPhoto.path;
                Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
                imageView.setImageBitmap(bitmap);
            }

            if(sessions.interactivePrompts.size() > 0) {
                mItemAdapter = new ReelItemAdapter(sessions.interactivePrompts);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mItemAdapter);
            }

        }
    };


}
