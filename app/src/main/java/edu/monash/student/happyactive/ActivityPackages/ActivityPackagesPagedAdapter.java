package edu.monash.student.happyactive.ActivityPackages;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.ActivityPackage;

/**
 * Manages data binding for activity package recyclerview
 */
public class ActivityPackagesPagedAdapter extends PagedListAdapter<ActivityPackage, ActivityPackageViewHolder> {

    private boolean isInProgress;

    public ActivityPackagesPagedAdapter(@NonNull DiffUtil.ItemCallback diffcallback, boolean isInProgress){
        super(diffcallback);
        this.isInProgress = isInProgress;
    }

    @NonNull
    @Override
    public ActivityPackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ActivityPackageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activitypackage_list_content, parent, false),parent.getContext(), this.isInProgress);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityPackageViewHolder holder, int i) {
        holder.bindTo(getItem(i));
    }

}