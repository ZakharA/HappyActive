package edu.monash.student.happyactive.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.monash.student.happyactive.fragments.Reports.AverageChartSliderCommonFragment;

/**
 * This adapter is used for setting up the slider of the stat comparison reports/average reports
 * page.
 */
public class AverageChartsSlidePagerAdapter extends FragmentStateAdapter {

    private Context mContext;

    /**
     * Constructor
     * @param fragment
     * @param mContext
     */
    public AverageChartsSlidePagerAdapter(@NonNull Fragment fragment, Context mContext) {
        super(fragment);
        this.mContext = mContext;
    }

    /**
     * Creates an instance of the fragment average chart slider common.
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return AverageChartSliderCommonFragment.newInstance(position);
    }

    /**
     * Fetches number of items(pages).
     * @return
     */
    @Override
    public int getItemCount() {
        return 3;
    }
}
