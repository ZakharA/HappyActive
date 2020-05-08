package edu.monash.student.happyactive.memoryReel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.fragments.CameraFragmentArgs;


public class PhotoCollageFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_photo_collage, container, false);
    }
}
