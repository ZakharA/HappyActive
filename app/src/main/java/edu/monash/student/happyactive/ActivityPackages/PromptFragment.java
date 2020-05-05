package edu.monash.student.happyactive.ActivityPackages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.viewModels.ActivitySessionViewModel;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.InteractivePrompt;
import edu.monash.student.happyactive.data.relationships.SessionWithPrompts;


public class PromptFragment extends Fragment {

    private ActivitySessionViewModel mSessionViewModel;
    private long mActivityId;
    private TextInputEditText mTextPrompt;
    private InteractivePrompt mInteractivePrompt;

    public PromptFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PromptFragment newInstance(long activityId) {
        PromptFragment fragment = new PromptFragment();
        Bundle args = new Bundle();
        args.putLong("activityId", activityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInteractivePrompt = new InteractivePrompt();
        if (getArguments() != null) {
            mActivityId = getArguments().getLong("activityId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_prompt, container, false);
        mTextPrompt = (TextInputEditText) view.findViewById(R.id.textPrompt);
        mTextPrompt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<InteractivePrompt> promptList = mSessionViewModel.getSessionWithPrompts();
                if(!taskExistsIn(promptList, mSessionViewModel.getTaskOnDisplay().id)) {
                    mInteractivePrompt.sessionId = mSessionViewModel.getSessionId();
                    mInteractivePrompt.taskId = mSessionViewModel.getTaskOnDisplay().id;
                    mInteractivePrompt.answer = s.toString();
                    mSessionViewModel.getSessionWithPrompts().add(mInteractivePrompt);
                } else {
                   promptList.get( promptList.indexOf(mInteractivePrompt)).answer = s.toString();
                }
            }
        });

        return view;
    }

    private boolean taskExistsIn(List<InteractivePrompt> promptList, long id) {
        boolean result = false;
        for(InteractivePrompt prompt: promptList){
             result = prompt.taskId == id ?  true :  false;
        }
        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSessionViewModel = new ViewModelProvider(requireActivity(),
                new ActivitySessionViewModel.Factory(getActivity().getApplication(), mActivityId)).get(ActivitySessionViewModel.class);
    }

}
