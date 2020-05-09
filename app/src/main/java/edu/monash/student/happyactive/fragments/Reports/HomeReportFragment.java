package edu.monash.student.happyactive.fragments.Reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.monash.student.happyactive.R;

public class HomeReportFragment extends Fragment {

    ImageView compareHistoryImg;
    ImageView compareAverageImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.acitivity_report_main, container, false);
        compareHistoryImg = view.findViewById(R.id.compareHistoryImg);
        compareAverageImg = view.findViewById(R.id.compareAverageImg);

        compareHistoryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Navigation.findNavController(view).navigate(
                     HomeReportFragmentDirections.showHistory()
              );
            }
        });

        compareAverageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Navigation.findNavController(view).navigate(
                       HomeReportFragmentDirections.showCompareAverage()
               );
            }
        });

        return view;
    }
}
