package edu.monash.student.happyactive.memoryReel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.monash.student.happyactive.R;


public class MemoryReelItemFragment extends Fragment {


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memory_reel_item, container, false);
    }
}
