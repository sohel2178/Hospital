package com.baudiabatash.hospital.Navigation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baudiabatash.hospital.Adapter.PatientAdapter;
import com.baudiabatash.hospital.Adapter.PatientReleaseAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyCabinDBAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyDoctorDBAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyPatientDBAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyReleasePatientDBAdapter;
import com.baudiabatash.hospital.Model.Cabin;
import com.baudiabatash.hospital.Model.Doctor;
import com.baudiabatash.hospital.Model.Patient;
import com.baudiabatash.hospital.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReleaseFragment extends Fragment implements PatientReleaseAdapter.UpdateDataFromId{

    private RecyclerView recyclerView;

    private ActionBar actionBar;


    private List<Patient> patientList;
    PatientReleaseAdapter adapter;

    private MyReleasePatientDBAdapter dbReleasePatient;
    private MyCabinDBAdapter dbCabin;
    private MyDoctorDBAdapter dbDoctor;

    private List<String> doctorNames,cabinNames;



    public ReleaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        cabinNames = new ArrayList<>();
        doctorNames = new ArrayList<>();

        openDb();
        patientList = new ArrayList<>();
        adapter = new PatientReleaseAdapter(getActivity(),patientList);
        adapter.setUpdateDatafromId(this);

        patientList.addAll(dbReleasePatient.getAllPatient());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_release, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }


    private void openDb() {
        dbReleasePatient = new MyReleasePatientDBAdapter(getActivity());
        dbCabin = new MyCabinDBAdapter(getActivity());
        dbDoctor = new MyDoctorDBAdapter(getActivity());

        dbReleasePatient.open();
        dbCabin.open();
        dbDoctor.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        closeDb();
    }

    private void closeDb() {
        dbReleasePatient.close();
        dbDoctor.close();
        dbCabin.close();
    }

    @Override
    public void setDoctor(TextView doctorName, int doctorId) {

        Doctor doctor = dbDoctor.getDoctor(doctorId);
        doctorName.setText(doctor.getName());
        // Fill the DoctorNames Array List
        doctorNames.add(doctor.getName());

    }

    @Override
    public void setCabin(TextView cabinName, int cabinId) {
        Cabin cabin = dbCabin.getCabin(cabinId);
        cabinName.setText(cabin.getName());
        // Fill the Cabin Array List
        cabinNames.add(cabin.getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        actionBar.setTitle("Release Patient List");
    }
}
