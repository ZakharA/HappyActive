package edu.monash.student.happyactive.ActivityPackages;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.ActivityPackage;

public class ActivityPackagesPagedAdapter extends PagedListAdapter<ActivityPackage, ActivityPackageViewHolder> {



    public ActivityPackagesPagedAdapter(@NonNull DiffUtil.ItemCallback diffcallback){
        super(diffcallback);
    }

    @NonNull
    @Override
    public ActivityPackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ActivityPackageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activitypackage_list_content, parent, false),parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityPackageViewHolder holder, int i) {
        holder.bindTo(getItem(i));
    }

}