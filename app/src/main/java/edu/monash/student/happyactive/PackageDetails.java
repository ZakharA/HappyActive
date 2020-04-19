package edu.monash.student.happyactive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import edu.monash.student.happyactive.fragments.PackageDetailsFragment;

public class PackageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_details_activity);
        long activityId = getIntent().getLongExtra(PackageDetailsFragment.ACTIVITY_ID, 0l);
        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putLong(PackageDetailsFragment.ACTIVITY_ID, activityId );

            PackageDetailsFragment myFragment = PackageDetailsFragment.newInstance();
            myFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, myFragment)
                    .commitNow();
        }
    }
}
