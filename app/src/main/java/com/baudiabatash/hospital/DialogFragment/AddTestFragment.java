package com.baudiabatash.hospital.DialogFragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.baudiabatash.hospital.CustomView.MyEditText;
import com.baudiabatash.hospital.DatabaseAdapter.MyTestDBAdapter;
import com.baudiabatash.hospital.Model.Notes;
import com.baudiabatash.hospital.Model.Test;
import com.baudiabatash.hospital.R;
import com.baudiabatash.hospital.Utility.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTestFragment extends DialogFragment implements View.OnClickListener{

    private MyEditText etTest;
    private Button btnAdd,btnCancel;

    private MyTestDBAdapter dbTest;

    private int patient_id;


    public AddTestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!= null){
            patient_id = getArguments().getInt(Constant.PATIENT_ID);
        }

        openDb();
    }

    private void openDb() {
        dbTest = new MyTestDBAdapter(getActivity());
        dbTest.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_test, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        etTest= (MyEditText) view.findViewById(R.id.test);
        btnCancel = (Button) view.findViewById(R.id.cancel);
        btnAdd = (Button) view.findViewById(R.id.add);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.add:

                String testStr = etTest.getText().toString().trim();

                if(TextUtils.isEmpty(testStr)){
                    etTest.requestFocus();
                    Toast.makeText(getActivity(), "Test is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Date date = new Date();
                String dateStr =dateToString(date);
                Test test = new Test(dateStr,testStr,0,patient_id);

                if(dbTest.insertNote(test)>0){
                    Intent intent = new Intent();
                    intent.putExtra("test",testStr);
                    intent.putExtra("date",dateStr);
                    getTargetFragment().onActivityResult(2, Activity.RESULT_OK,intent);
                    getDialog().dismiss();
                }
                break;

            case R.id.cancel:
                getDialog().dismiss();
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbTest.close();
    }

    private String dateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        return formatter.format(date);
    }
}
