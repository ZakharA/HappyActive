package edu.monash.student.happyactive.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.monash.student.happyactive.fragments.Reports.HistoryChartSliderCommonFragment;

public class HistoryChartsSlidePagerAdapter extends FragmentStateAdapter {

    private Context mContext;

    public HistoryChartsSlidePagerAdapter(@NonNull Fragment fragment, Context mContext) {
        super(fragment);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return HistoryChartSliderCommonFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
