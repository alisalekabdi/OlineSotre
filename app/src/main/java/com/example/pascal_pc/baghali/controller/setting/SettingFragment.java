package com.example.pascal_pc.baghali.controller.setting;


import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.service.PullService;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    private EditText mTimeEditText;
    private Spinner mTimeSpinner;
    private Button mConfirmBtn;
    private int mChooseTime=0;

    private ArrayAdapter<CharSequence> mTimeAdapter;

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.setting_view, container, false);

        mTimeEditText = view.findViewById(R.id.time_edit_text);
        mConfirmBtn = view.findViewById(R.id.change_time_btn);
        mTimeSpinner = view.findViewById(R.id.time_spinner);

        mTimeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.default_time_spinner, R.layout.support_simple_spinner_dropdown_item);
        mTimeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mTimeSpinner.setAdapter(mTimeAdapter);

        mTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        mChooseTime=3;
                        break;
                    case 2:
                        mChooseTime=5;
                        break;
                    case 3:
                        mChooseTime=8;
                        break;
                    case 4:
                        mChooseTime=12;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimeEditText.getText().toString().length()>0){
                    mChooseTime= Integer.parseInt(mTimeEditText.getText().toString());
                }
                if (mChooseTime==0){
                    Toast.makeText(getActivity(), "You don't change time", Toast.LENGTH_SHORT).show();
                }else {
                    Log.e("alisalek", "Okey shod ");
                    PullService.setServiceAlarm(getActivity(),true,mChooseTime);
                    getActivity().finish();
                }
            }
        });

        return view;
    }

}
