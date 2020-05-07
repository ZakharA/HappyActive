package edu.monash.student.happyactive;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivityPackageViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InProgressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InProgressFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAB_POS = "Tab Position";

    private Integer tabPosition;
    protected ActivityPackageViewModel mActivityPackageViewModel;

    public InProgressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param tabPosition Tab Position.
     * @return A new instance of fragment InProgressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InProgressFragment newInstance(Integer tabPosition) {
        InProgressFragment fragment = new InProgressFragment();
        Bundle args = new Bundle();
        args.putInt(TAB_POS, tabPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityPackageViewModel = new ViewModelProvider(this,
                new ActivityPackageViewModel.Factory(getActivity().getApplication())).get(ActivityPackageViewModel.class);
        if (getArguments() != null) {
            tabPosition = getArguments().getInt(TAB_POS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activitypackage_list, container, false);
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }
}
