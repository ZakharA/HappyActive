package edu.monash.student.happyactive.fragments.ActivityDetails;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivityPackageViewModel;
import edu.monash.student.happyactive.R;

/**
 * manages the logic of showing package details on the screen
 * and starting a new session for the acitvity package
 */
public class PackageDetailsFragment extends Fragment {

    public static final String ACTIVITY_ID = "activity_id";
    private long selectedActivityId;
    private ActivityPackageViewModel mPackageViewModel;


    public static PackageDetailsFragment newInstance() {
        return new PackageDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedActivityId = PackageDetailsFragmentArgs.fromBundle(getArguments()).getActivityId();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.package_details_fragment, container, false);
        Button startButton = view.findViewById(R.id.start_activity_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(
                        PackageDetailsFragmentDirections.startSession().setActivityId(selectedActivityId)
                );
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPackageViewModel =  new ViewModelProvider(requireActivity(),
                new ActivityPackageViewModel.Factory(getActivity().getApplication())).get(ActivityPackageViewModel.class);

        mPackageViewModel.getActivityPackageById(selectedActivityId).observe(getViewLifecycleOwner(), activity -> {
            ((ImageView) view.findViewById(R.id.detail_image)).setImageResource(getResources()
                            .getIdentifier(activity.imagePath.split("[.]")[0], "drawable", "edu.monash.student.happyactive"));
            ((TextView) view.findViewById(R.id.activity_title)).setText(activity.title);
            ((TextView) view.findViewById(R.id.activity_description)).setText(activity.description);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
