package edu.monash.student.happyactive;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.monash.student.happyactive.ActivityPackages.ActivityPackagesPagedAdapter;
import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivityPackageViewModel;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.fragments.PackageDetailsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of ActivityPackages. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ActivityPackageListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    protected ActivityPackageViewModel mActivityPackageViewModel;
    public static String mPackageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitypackage_list);

        RecyclerView recyclerView = findViewById(R.id.activitypackage_list);
        assert recyclerView != null;

        mPackageName = getPackageName();

        mActivityPackageViewModel = new ViewModelProvider(this,
                new ActivityPackageViewModel.Factory(this.getApplication())).get(ActivityPackageViewModel.class);

        ActivityPackagesPagedAdapter activityPackagesPagedAdapter = new ActivityPackagesPagedAdapter(diffCallback);
        mActivityPackageViewModel.getActivityPackagesPages().observe(this, activityPackagesPagedAdapter::submitList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
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
