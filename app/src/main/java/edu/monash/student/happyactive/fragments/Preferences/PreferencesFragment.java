package edu.monash.student.happyactive.fragments.Preferences;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import edu.monash.student.happyactive.Preferences.PreferencesViewModel;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.UserPref;
import edu.monash.student.happyactive.data.enumerations.ArthritisCondition;
import edu.monash.student.happyactive.data.enumerations.PrefAccess;
import edu.monash.student.happyactive.data.enumerations.PrefFrequency;
import edu.monash.student.happyactive.data.enumerations.UserAge;
import edu.monash.student.happyactive.data.enumerations.UserGender;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PreferencesFragment extends Fragment {

    private CheckBox bakingCheckBox;
    private CheckBox gardeningCheckBox;
    private CheckBox walkingCheckBox;
    private CheckBox playingCheckBox;
    private CheckBox shoppingCheckBox;
    private RadioGroup parkRadioGroup;
    private RadioGroup arthritisConditionRadioGroup;
    private RadioGroup backyardRadioGroup;
    private RadioGroup activityTimeRadioGroup;
    private RadioGroup activityDistanceRadioGroup;
    private RadioGroup userAgeRadioGroup;
    private RadioGroup userGenderRadioGroup;
    private Button submitPrefFormButton;
    private View prefView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prefView = inflater.inflate(R.layout.fragment_preferences, container, false);
        bakingCheckBox = prefView.findViewById(R.id.bakingCheckBox);
        gardeningCheckBox = prefView.findViewById(R.id.gardeningCheckBox);
        walkingCheckBox = prefView.findViewById(R.id.walkingCheckBox);
        playingCheckBox = prefView.findViewById(R.id.playingCheckBox);
        shoppingCheckBox = prefView.findViewById(R.id.shoppingCheckBox);
        parkRadioGroup = prefView.findViewById(R.id.parkRadioGroup);
        arthritisConditionRadioGroup = prefView.findViewById(R.id.arthritisCondtionRadioGroup);
        backyardRadioGroup = prefView.findViewById(R.id.backyardRadioGroup);
        activityTimeRadioGroup = prefView.findViewById(R.id.activityTimeRadioGroup);
        activityDistanceRadioGroup = prefView.findViewById(R.id.activityDistanceRadioGroup);
        userAgeRadioGroup = prefView.findViewById(R.id.userAgeRadioGroup);
        userGenderRadioGroup = prefView.findViewById(R.id.userGenderRadioGroup);
        submitPrefFormButton = prefView.findViewById(R.id.submitPrefFormButton);
        return prefView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PreferencesViewModel preferencesViewModel = new ViewModelProvider(this,
                new PreferencesViewModel.Factory(getActivity().getApplication())).get(PreferencesViewModel.class);

        submitPrefFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPref userPref = new UserPref(1);
                String hobby = "";
                if (bakingCheckBox.isChecked() || walkingCheckBox.isChecked() || gardeningCheckBox.isChecked() ||
                        playingCheckBox.isChecked() || shoppingCheckBox.isChecked()) {
                    if (bakingCheckBox.isChecked()) {
                        hobby += bakingCheckBox.getText() + ",";
                    }
                    if (walkingCheckBox.isChecked()) {
                        hobby += walkingCheckBox.getText() + ",";
                    }
                    if (gardeningCheckBox.isChecked()) {
                        hobby += gardeningCheckBox.getText() + ",";
                    }
                    if (playingCheckBox.isChecked()) {
                        hobby += playingCheckBox.getText() + ",";
                    }
                    if (shoppingCheckBox.isChecked()) {
                        hobby += shoppingCheckBox.getText() + ",";
                    }
                }
                userPref.setHobbyList(hobby);

                if (backyardRadioGroup.getCheckedRadioButtonId() != -1) {
                    int selectedId = backyardRadioGroup.getCheckedRadioButtonId();
                    RadioButton radioButtonBackyardAccess = prefView.findViewById(selectedId);
                    for (PrefAccess access : PrefAccess.values()) {
                        if (access.getValue().equals(radioButtonBackyardAccess.getText())) {
                            userPref.setGardenAccess(access);
                            break;
                        }
                    }
                }
                else {
                    userPref.setGardenAccess(null);
                }

                if (parkRadioGroup.getCheckedRadioButtonId() != -1) {
                    int selectedId = parkRadioGroup.getCheckedRadioButtonId();
                    RadioButton radioButtonParkAccess= prefView.findViewById(selectedId);
                    for (PrefAccess access : PrefAccess.values()) {
                        if (access.getValue().equals(radioButtonParkAccess.getText())) {
                            userPref.setParkAccess(access);
                            break;
                        }
                    }
                }
                else {
                    userPref.setParkAccess(null);
                }

                if (arthritisConditionRadioGroup.getCheckedRadioButtonId() != -1) {
                    int selectedId = arthritisConditionRadioGroup.getCheckedRadioButtonId();
                    RadioButton radioButtonArthritis = prefView.findViewById(selectedId);
                    for (ArthritisCondition condition : ArthritisCondition.values()) {
                        if (condition.getValue().equals(radioButtonArthritis.getText())) {
                            userPref.setArthritisCondition(condition);
                            break;
                        }
                    }
                }
                else {
                    userPref.setArthritisCondition(null);
                }

                if (activityTimeRadioGroup.getCheckedRadioButtonId() != -1) {
                    int selectedId = activityTimeRadioGroup.getCheckedRadioButtonId();
                    RadioButton radioButtonActivityTime = prefView.findViewById(selectedId);
                    for (PrefFrequency freq : PrefFrequency.values()) {
                        if (freq.getValue().equals(radioButtonActivityTime.getText().subSequence(0, radioButtonActivityTime.getText().length() - 6))) {
                            userPref.setActivityTime(freq);
                            break;
                        }
                    }
                }
                else {
                    userPref.setActivityTime(null);
                }

                if (activityDistanceRadioGroup.getCheckedRadioButtonId() != -1) {
                    int selectedId = activityDistanceRadioGroup.getCheckedRadioButtonId();
                    RadioButton radioButtonDistance = prefView.findViewById(selectedId);
                    for (PrefFrequency freq : PrefFrequency.values()) {
                        if (freq.getValue().equals(radioButtonDistance.getText().subSequence(0, radioButtonDistance.getText().length() - 3))) {
                            userPref.setActivityDistance(freq);
                            break;
                        }
                    }
                }
                else {
                    userPref.setActivityDistance(null);
                }

                if (userAgeRadioGroup.getCheckedRadioButtonId() != -1) {
                    int selectedId = userAgeRadioGroup.getCheckedRadioButtonId();
                    RadioButton radioButtonAge = prefView.findViewById(selectedId);
                    for (UserAge age : UserAge.values()) {
                        if (age.getValue().equals(radioButtonAge.getText())) {
                            userPref.setUserAge(age);
                            break;
                        }
                    }
                }
                else {
                    userPref.setUserAge(null);
                }

                if (userGenderRadioGroup.getCheckedRadioButtonId() != -1) {
                    int selectedId = userGenderRadioGroup.getCheckedRadioButtonId();
                    RadioButton radioButtonDistance = prefView.findViewById(selectedId);
                    for (UserGender gender : UserGender.values()) {
                        if (gender.getValue().equals(radioButtonDistance.getText())) {
                            userPref.setUserGender(gender);
                            break;
                        }
                    }
                }
                else {
                    userPref.setUserGender(null);
                }

                preferencesViewModel.updatePreferences(userPref);
                Navigation.findNavController(prefView).navigate(
                        PreferencesFragmentDirections.goBackToHomeFromPreferences()
                );

            }
        });
    }
}
