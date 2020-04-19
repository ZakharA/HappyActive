package edu.monash.student.happyactive;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.monash.student.happyactive.ui.packagedetails.PackageDetailsFragment;

public class PackageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_details_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PackageDetailsFragment.newInstance())
                .commitNow();
        }
    }
}
