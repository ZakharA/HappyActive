package edu.monash.student.happyactive.fragments.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.monash.student.happyactive.Adapters.SectionsPagerAdapter;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.Reports.OverallActivity.OverallActivityViewModel;

import static android.content.Context.MODE_PRIVATE;

public class ActivitiesHomeFragment extends Fragment {

    private View view;
    private ViewPager2 viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private TabLayout tabs;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activities_home, container, false);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getContext());
        viewPager = view.findViewById(R.id.view_pager);
        tabs = view.findViewById(R.id.tabs);

        viewPager.setAdapter(sectionsPagerAdapter);

        new TabLayoutMediator(tabs, viewPager,
                (tab, position) -> tab.setText(sectionsPagerAdapter.getTabTitles()[position])
        ).attach();
        return view;

    }
}