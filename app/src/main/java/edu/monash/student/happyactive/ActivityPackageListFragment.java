package edu.monash.student.happyactive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.monash.student.happyactive.ActivityPackages.ActivityPackagesPagedAdapter;
import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivityPackageViewModel;
import edu.monash.student.happyactive.data.entities.ActivityPackage;

/**
 * An activity representing a list of ActivityPackages. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a ActivityPackageDetailActivity representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ActivityPackageListFragment extends Fragment {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    protected ActivityPackageViewModel mActivityPackageViewModel;
    public static String mPackageName;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View packageListView = inflater.inflate(R.layout.fragment_activitypackage_list, container, false);
        recyclerView = packageListView.findViewById(R.id.activitypackage_list);
        assert recyclerView != null;
        mPackageName = getContext().getPackageName();
        return packageListView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivityPackageViewModel = new ViewModelProvider(this,
                new ActivityPackageViewModel.Factory(getActivity().getApplication())).get(ActivityPackageViewModel.class);

        ActivityPackagesPagedAdapter activityPackagesPagedAdapter = new ActivityPackagesPagedAdapter(diffCallback);
        mActivityPackageViewModel.getActivityPackagesPages().observe(getViewLifecycleOwner(), activityPackagesPagedAdapter::submitList);
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
