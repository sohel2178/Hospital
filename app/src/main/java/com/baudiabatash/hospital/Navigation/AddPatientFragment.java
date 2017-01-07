package com.baudiabatash.hospital.Navigation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.baudiabatash.hospital.R;
import com.baudiabatash.hospital.Utility.MyUtils;
import com.joanzapata.iconify.widget.IconTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPatientFragment extends Fragment implements View.OnClickListener{

    private EditText etDate;
    private IconTextView calendar;


    public AddPatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_patient, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        etDate = (EditText) view.findViewById(R.id.date);
        calendar = (IconTextView) view.findViewById(R.id.calendar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        calendar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        MyUtils.showDialogAndSetTime(getActivity(),etDate);

    }
}
