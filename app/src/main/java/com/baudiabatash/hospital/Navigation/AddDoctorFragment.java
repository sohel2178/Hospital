package com.baudiabatash.hospital.Navigation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baudiabatash.hospital.CustomView.MyEditText;
import com.baudiabatash.hospital.DatabaseAdapter.MyDoctorDBAdapter;
import com.baudiabatash.hospital.Model.Doctor;
import com.baudiabatash.hospital.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDoctorFragment extends Fragment implements View.OnClickListener{
    
    private MyEditText etName,etDesignation,etDegree,etOrganization;
    private Button btnAdd;

    private MyDoctorDBAdapter dbDoctor;// Instantiate,Open DB, Operation and Close


    public AddDoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openDb();
    }

    private void openDb() {
        dbDoctor = new MyDoctorDBAdapter(getActivity());
        dbDoctor.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbDoctor.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_doctor, container, false);
        
        initView(view);
        
        return view;
    }

    private void initView(View view) {
        etName = (MyEditText) view.findViewById(R.id.name);
        etDesignation = (MyEditText) view.findViewById(R.id.designation);
        etDegree = (MyEditText) view.findViewById(R.id.degree);
        etOrganization = (MyEditText) view.findViewById(R.id.organization);
        btnAdd = (Button) view.findViewById(R.id.add);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = etName.getText().toString().trim();
        String designation = etDesignation.getText().toString().trim();
        String degree = etDegree.getText().toString().trim();
        String organization = etOrganization.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            etName.requestFocus();
            Toast.makeText(getActivity(), "Name Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(designation)){
            etDesignation.requestFocus();
            Toast.makeText(getActivity(), "Designation Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(degree)){
            etDegree.requestFocus();
            Toast.makeText(getActivity(), "Degree Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(organization)){
            etOrganization.requestFocus();
            Toast.makeText(getActivity(), "Organization Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Doctor doctor = new Doctor(name,designation,degree,organization);
        addDoctorToDatabase(doctor);
    }

    private void addDoctorToDatabase(Doctor doctor) {
        long id =dbDoctor.insertRow(doctor);

        if(id>0){
            getFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment()).commit();
        }

        Log.d("ID",id+"");
    }
}
