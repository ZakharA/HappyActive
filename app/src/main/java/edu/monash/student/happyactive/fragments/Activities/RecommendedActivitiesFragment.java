package edu.monash.student.happyactive.fragments.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivityPackageViewModel;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.ActivityPackage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendedActivitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendedActivitiesFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAB_POS = "Tab Position";

    private Integer tabPosition;
    protected ActivityPackageViewModel mActivityPackageViewModel;
    private RecyclerView recyclerView;

    public RecommendedActivitiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param tabPosition Tab Position.
     * @return A new instance of fragment RecommendedActivitiesFragment.
     */
    public static RecommendedActivitiesFragment newInstance(Integer tabPosition) {
        RecommendedActivitiesFragment fragment = new RecommendedActivitiesFragment();
        Bundle args = new Bundle();
        args.putInt(TAB_POS, tabPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityPackageViewModel = new ViewModelProvider(this,
                new ActivityPackageViewModel.Factory(getActivity().getApplication())).get(ActivityPackageViewModel.class);
        if (getArguments() != null) {
            tabPosition = getArguments().getInt(TAB_POS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.activitypackage_list, container, false);
        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivityPackageViewModel = new ViewModelProvider(this,
                new ActivityPackageViewModel.Factory(getActivity().getApplication())).get(ActivityPackageViewModel.class);
    }

    private DiffUtil.ItemCallback<ActivityPackage> diffCallback = new DiffUtil.ItemCallback<ActivityPackage>() {
        @Override
        public boolean areItemsTheSame(@NonNull ActivityPackage activityPackage, @NonNull ActivityPackage newActivityPackage) {
            return activityPackage.id == newActivityPackage.id;
        }
        @Override
        public boolean areContentsTheSame(@NonNull ActivityPackage activityPackage, @NonNull ActivityPackage newActivityPackage) {
            return activityPackage.id == newActivityPackage.id;
        }
    };
}
