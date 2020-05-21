package edu.monash.student.happyactive.fragments.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.monash.student.happyactive.ActivityPackages.ActivityPackagesPagedAdapter;
import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivityPackageViewModel;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.memoryReel.HappyActiveRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InProgressActivitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InProgressActivitiesFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAB_POS = "Tab Position";

    private Integer tabPosition;
    protected ActivityPackageViewModel mActivityPackageViewModel;
    private View view;
    private HappyActiveRecyclerView recyclerView;
    private Button editPreferencesButton;

    public InProgressActivitiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param tabPosition Tab Position.
     * @return A new instance of fragment InProgressFragment.
     */
    public static InProgressActivitiesFragment newInstance(Integer tabPosition) {
        InProgressActivitiesFragment fragment = new InProgressActivitiesFragment();
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
        view = inflater.inflate(R.layout.activitypackage_list, container, false);
        recyclerView = (HappyActiveRecyclerView) view.findViewById(R.id.activitypackage_list);
        TextView emptyView = view.findViewById(R.id.emptyRecyclerView);
        emptyView.setText("Awesome.\nYou have no incomplete activities.");
        recyclerView.setEmptyView(emptyView);
        editPreferencesButton = view.findViewById(R.id.editPreferencesButton);

        editPreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(
                        ActivitiesHomeFragmentDirections.showPreferencesFragment()
                );
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActivityPackagesPagedAdapter activityPackagesPagedAdapter = new ActivityPackagesPagedAdapter(diffCallback, true);
        mActivityPackageViewModel.getInProgressActivityPackagesAsPagedList().observe(getViewLifecycleOwner(), activityPackagesPagedAdapter::submitList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator (new DefaultItemAnimator());
        recyclerView.setAdapter(activityPackagesPagedAdapter);
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
