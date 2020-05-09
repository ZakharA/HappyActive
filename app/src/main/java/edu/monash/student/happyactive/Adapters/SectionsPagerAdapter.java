package edu.monash.student.happyactive.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.monash.student.happyactive.fragments.Activities.AllActivitiesFragment;
import edu.monash.student.happyactive.fragments.Activities.InProgressActivitiesFragment;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.fragments.Activities.RecommendedActivitiesFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private Context mContext;

    public SectionsPagerAdapter(@NonNull Fragment fragment, Context mContext) {
        super(fragment);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch(position){
            case 0:
                return InProgressActivitiesFragment.newInstance(position);
            case 1:
                return RecommendedActivitiesFragment.newInstance(position);
            case 2:
                return AllActivitiesFragment.newInstance(position);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        // Show 3 total pages.
        return 3;
    }

    public int[] getTabTitles() {
        return TAB_TITLES;
    }
}