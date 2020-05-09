package edu.monash.student.happyactive.ActivityPackages;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.fragments.Activities.ActivitiesHomeFragmentDirections;

public class ActivityPackageViewHolder extends RecyclerView.ViewHolder{
    final TextView mIdView;
    final TextView mContentView;
    final ImageView mImageView;
    private ActivityPackage activityPackage;
    private boolean isInProgress;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isInProgress) {
                Navigation.findNavController(view).navigate(
                        ActivitiesHomeFragmentDirections.startSession().setActivityId(activityPackage.id)
                );
            } else {
                Navigation.findNavController(view).navigate(
                        ActivitiesHomeFragmentDirections.showPackageDetails().setActivityId(activityPackage.id)
                );
            }

        }
    };
    private final Resources res;
    private final String mPackageName;


    public ActivityPackageViewHolder(View itemView, Context mContext, boolean isInProgress) {
        super(itemView);
        mPackageName = mContext.getPackageName();
        mIdView = (TextView) itemView.findViewById(R.id.id_text);
        mContentView = (TextView) itemView.findViewById(R.id.content);
        mImageView = (ImageView) itemView.findViewById(R.id.package_image);
        res = mContext.getResources();
        this.isInProgress = isInProgress;
    }


    void bindTo(final ActivityPackage activityPackage){
        this.activityPackage = activityPackage;
        mIdView.setText(String.valueOf( activityPackage.title));
        mContentView.setText(activityPackage.description);
        mImageView.setImageResource(res.getIdentifier(activityPackage.imagePath.split("[.]")[0],  "drawable", mPackageName));
        itemView.setOnClickListener(mOnClickListener);
    }
}