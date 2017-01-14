package com.baudiabatash.hospital.DialogFragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.baudiabatash.hospital.CustomView.MyEditText;
import com.baudiabatash.hospital.DatabaseAdapter.MyNotesDBAdapter;
import com.baudiabatash.hospital.Model.Notes;
import com.baudiabatash.hospital.R;
import com.baudiabatash.hospital.Utility.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNotesFragment extends DialogFragment implements View.OnClickListener{

    private MyEditText etNotes;

    private Button btnAdd,btnCancel;

    private MyNotesDBAdapter dbNotes;

    private int patient_id;


    public AddNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openDb();

        if(getArguments()!=null){
            patient_id = getArguments().getInt(Constant.PATIENT_ID);

            Log.d("PatientID",String.valueOf(patient_id));
        }


    }

    private void openDb() {
        dbNotes = new MyNotesDBAdapter(getActivity());
        dbNotes.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_notes, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        etNotes = (MyEditText) view.findViewById(R.id.notes);
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

                String notes = etNotes.getText().toString().trim();

                if(TextUtils.isEmpty(notes)){
                    etNotes.requestFocus();
                    Toast.makeText(getActivity(), "Notes is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Date date = new Date();
                String dateStr =dateToString(date);
                Notes note = new Notes(dateStr,notes,patient_id);

                if(dbNotes.insertNote(note)>0){
                    Intent intent = new Intent();
                    intent.putExtra("notes",notes);
                    intent.putExtra("date",dateStr);
                    getTargetFragment().onActivityResult(1, Activity.RESULT_OK,intent);
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
        dbNotes.close();
    }

    private String dateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        return formatter.format(date);
    }
}
