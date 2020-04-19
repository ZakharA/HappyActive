package edu.monash.student.happyactive.fragments;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.monash.student.happyactive.ActivityPackageDetailActivity;
import edu.monash.student.happyactive.ActivityPackageListActivity;
import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivityPackageViewModel;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.ActivityPackage;

/**
 * A fragment representing a single ActivityPackage detail screen.
 * This fragment is either contained in a {@link ActivityPackageListActivity}
 * in two-pane mode (on tablets) or a {@link ActivityPackageDetailActivity}
 * on handsets.
 */
public class ActivityPackageDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private ActivityPackage mItem;
    private ActivityPackageViewModel mPackageViewModel;
    private CollapsingToolbarLayout appBarLayout;
    private View rootView;
    private long selectedActivityId;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ActivityPackageDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            Activity activity = this.getActivity();
            selectedActivityId = activity.getIntent().getExtras().getLong(ActivityPackageDetailFragment.ARG_ITEM_ID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_activitypackage_detail, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPackageViewModel =  new ViewModelProvider(requireActivity(), new ActivityPackageViewModel.Factory(getActivity().getApplication())).get(ActivityPackageViewModel.class);
        mPackageViewModel.getActivityPackageById(selectedActivityId).observe(getViewLifecycleOwner(), activity -> {
            //((TextView) rootView.findViewById(R.id.detail_fragment)).setText(activity.description);
        });
    }
}
