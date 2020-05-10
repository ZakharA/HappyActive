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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import edu.monash.student.happyactive.Preferences.PreferencesViewModel;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.UserPref;
import edu.monash.student.happyactive.data.enumerations.PrefAccess;
import edu.monash.student.happyactive.data.enumerations.PrefFrequency;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PreferencesFragment extends Fragment {

    private CheckBox cookingCheckBox;
    private CheckBox gardeningCheckBox;
    private CheckBox walkingCheckBox;
    private EditText otherEditText;
    private RadioGroup radioGroupExercise;
    private RadioGroup radioGroupGrandparentFreq;
    private RadioGroup backyardRadioGroup;
    private RadioGroup dogRadioGroup;
    private Button submitPrefFormButton;
    private View prefView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prefView = inflater.inflate(R.layout.fragment_preferences, container, false);
        cookingCheckBox = prefView.findViewById(R.id.cookingCheckBox);
        gardeningCheckBox = prefView.findViewById(R.id.gardeningCheckBox);
        walkingCheckBox = prefView.findViewById(R.id.walkingCheckBox);
        otherEditText = prefView.findViewById(R.id.otherEditText);
        radioGroupExercise = prefView.findViewById(R.id.radioGroupExercise);
        radioGroupGrandparentFreq = prefView.findViewById(R.id.radioGroupGrandparentFreq);
        backyardRadioGroup = prefView.findViewById(R.id.backyardRadioGroup);
        dogRadioGroup = prefView.findViewById(R.id.dogRadioGroup);
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
                if (cookingCheckBox.isChecked() || walkingCheckBox.isChecked() || gardeningCheckBox.isChecked() || otherEditText.getText() != null) {
                    if (cookingCheckBox.isChecked()) {
                        hobby += cookingCheckBox.getText() + ",";
                    }
                    if (walkingCheckBox.isChecked()) {
                        hobby += walkingCheckBox.getText() + ",";
                    }
                    if (gardeningCheckBox.isChecked()) {
                        hobby += gardeningCheckBox.getText() + ",";
                    }
                    if(otherEditText.getText() != null || !otherEditText.getText().equals("Others?")) {
                        hobby += otherEditText.getText() + ",";
                    }
                }
                userPref.setHobbyList(hobby);

                if (radioGroupExercise.getCheckedRadioButtonId() != -1){
                    int selectedId = radioGroupExercise.getCheckedRadioButtonId();
                    RadioButton radioButtonExercise = prefView.findViewById(selectedId);
                    for (PrefFrequency freq : PrefFrequency.values()) {
                        if (freq.getValue().equals(radioButtonExercise.getText())) {
                            userPref.setExerciseFreq(freq);
                            break;
                        }
                    }
                }
                else {
                    userPref.setExerciseFreq(null);
                }

                if (radioGroupGrandparentFreq.getCheckedRadioButtonId() != -1) {
                    int selectedId = radioGroupGrandparentFreq.getCheckedRadioButtonId();
                    RadioButton radioButtonGrandParentFreq = prefView.findViewById(selectedId);
                    for (PrefFrequency freq : PrefFrequency.values()) {
                        if (freq.getValue().equals(radioButtonGrandParentFreq.getText())) {
                            userPref.setGrandparentInteractionFreq(freq);
                            break;
                        }
                    }
                }
                else {
                    userPref.setGrandparentInteractionFreq(null);
                }

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

                if (dogRadioGroup.getCheckedRadioButtonId() != -1) {
                    int selectedId = dogRadioGroup.getCheckedRadioButtonId();
                    RadioButton radioButtonDogAccess= prefView.findViewById(selectedId);
                    for (PrefAccess access : PrefAccess.values()) {
                        if (access.getValue().equals(radioButtonDogAccess.getText())) {
                            userPref.setDogAccess(access);
                            break;
                        }
                    }
                }
                else {
                    userPref.setDogAccess(null);
                }

                preferencesViewModel.updatePreferences(userPref);
                Navigation.findNavController(prefView).navigate(
                        PreferencesFragmentDirections.goBackToHomeFromPreferences()
                );

            }
        });
    }
}
