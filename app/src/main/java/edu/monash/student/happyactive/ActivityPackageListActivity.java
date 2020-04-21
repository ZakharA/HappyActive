package edu.monash.student.happyactive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivityPackageViewModel;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.fragments.PackageDetailsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of ActivityPackages. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a ActivityPackageDetailActivity representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ActivityPackageListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    protected ActivityPackageViewModel mActivityPackageViewModel;
    private SimpleItemRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitypackage_list);

        View recyclerView = findViewById(R.id.activitypackage_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        mActivityPackageViewModel = new ViewModelProvider(this,
                new ActivityPackageViewModel.Factory(this.getApplication())).get(ActivityPackageViewModel.class);
        mActivityPackageViewModel.getAllActivityPackages().observe(this, new Observer<List<ActivityPackage>>() {
            @Override
            public void onChanged(List<ActivityPackage> activityPackages) {
                adapter.setValues(activityPackages);
            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new SimpleItemRecyclerViewAdapter(this, new ArrayList<ActivityPackage>(), mTwoPane);
        recyclerView.setAdapter(adapter);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ActivityPackageListActivity mParentActivity;
        private  List<ActivityPackage> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityPackage activityPackage = (ActivityPackage) view.getTag();
                //mParentActivity.mActivityPackageViewModel.setSelectedActivityPackage(activityPackage);

                Context context = view.getContext();
                Intent intent = new Intent(context, PackageDetails.class);
                intent.putExtra(PackageDetailsFragment.ACTIVITY_ID, activityPackage.id);

                context.startActivity(intent);
            }
        };

        SimpleItemRecyclerViewAdapter(ActivityPackageListActivity parent,
                                      List<ActivityPackage> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        public List<ActivityPackage> getValues() {
            return mValues;
        }

        public void setValues(List<ActivityPackage> mValues) {
            this.mValues = mValues;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activitypackage_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(String.valueOf( mValues.get(position).title));
            holder.mContentView.setText(mValues.get(position).description);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
