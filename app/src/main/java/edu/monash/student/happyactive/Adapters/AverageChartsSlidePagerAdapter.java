package edu.monash.student.happyactive.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.monash.student.happyactive.fragments.Reports.AverageChartSliderCommonFragment;

public class AverageChartsSlidePagerAdapter extends FragmentStateAdapter {

    private Context mContext;

    public AverageChartsSlidePagerAdapter(@NonNull Fragment fragment, Context mContext) {
        super(fragment);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return AverageChartSliderCommonFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
