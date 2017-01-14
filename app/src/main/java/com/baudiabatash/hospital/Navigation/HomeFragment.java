package com.baudiabatash.hospital.Navigation;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baudiabatash.hospital.Adapter.PatientAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyCabinDBAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyDoctorDBAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyPatientDBAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyReleasePatientDBAdapter;
import com.baudiabatash.hospital.Fragments.DetailFragments.PatientFragment;
import com.baudiabatash.hospital.Model.Cabin;
import com.baudiabatash.hospital.Model.Doctor;
import com.baudiabatash.hospital.Model.Patient;
import com.baudiabatash.hospital.R;
import com.baudiabatash.hospital.Utility.Constant;
import com.baudiabatash.hospital.Utility.UserLocalStore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements
        PatientAdapter.PatientClickListener,View.OnClickListener{

    private RecyclerView recyclerView;

    private FloatingActionButton fabAdd;

    private List<Patient> patientList;
    PatientAdapter adapter;

    private MyPatientDBAdapter dbPatient;
    private MyCabinDBAdapter dbCabin;
    private MyDoctorDBAdapter dbDoctor;
    private MyReleasePatientDBAdapter dbReleasePatient;

    private List<String> doctorNames,cabinNames;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        cabinNames = new ArrayList<>();
        doctorNames = new ArrayList<>();

        openDb();
        patientList = new ArrayList<>();
        adapter = new PatientAdapter(getActivity(),patientList);
        adapter.setPatientClickListener(this);

        patientList.addAll(dbPatient.getAllPatient());




    }

    private void openDb() {
        dbPatient = new MyPatientDBAdapter(getActivity());
        dbCabin = new MyCabinDBAdapter(getActivity());
        dbDoctor = new MyDoctorDBAdapter(getActivity());
        dbReleasePatient = new MyReleasePatientDBAdapter(getActivity());

        dbPatient.open();
        dbCabin.open();
        dbDoctor.open();
        dbReleasePatient.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        fabAdd = (FloatingActionButton) view.findViewById(R.id.add);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        closeDb();
    }

    private void closeDb() {
        dbPatient.close();
        dbDoctor.close();
        dbCabin.close();
        dbReleasePatient.close();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fabAdd.setOnClickListener(this);
    }



    @Override
    public void itemClick(int position) {
        Patient patient = patientList.get(position);
        Bundle bundle =new Bundle();
        bundle.putSerializable(Constant.PATIENT,patient);
        bundle.putString(Constant.DOCTOR_NAME,getDoctorName(patient.getRef_doctor_id()));
        bundle.putString(Constant.CABIN_NAME,getCabinName(patient.getAllocated_cabin_id()));

        PatientFragment patientFragment = new PatientFragment();
        patientFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.main_container,patientFragment)
                .setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_bottom)
                .addToBackStack(null).commit();
    }

    @Override
    public void delete(int position) {
        final Patient patient = patientList.get(position);

        // Prompt user To delete
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(getActivity());
        aBuilder.setTitle("Delete Operation")
                .setMessage("Do you Really want to delete?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Delete From database
                        // Update Cabin Status
                        // Update Adapter

                        // Delete From database
                        if(dbPatient.deletePatient(patient.getId())){
                            // Update Cabin Status
                            Cabin cabin = dbCabin.getCabin(patient.getAllocated_cabin_id());
                            cabin.setStatus(0);
                            if(dbCabin.updateCabin(cabin)){
                                // Update Adapter
                                adapter.deletePatient(patient);

                                // Dismiss the Dialog
                                dialog.dismiss();
                            }
                        }

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();

    }

    @Override
    public void release(int position) {
        Patient patient = patientList.get(position);
        dbPatient.deletePatient(patient);
        adapter.deletePatient(patient);

        // Update Cabin
        Cabin cabin = dbCabin.getCabin(patient.getAllocated_cabin_id());
        cabin.setStatus(0);
        dbCabin.updateCabin(cabin);

        dbReleasePatient.insertPatient(patient);

    }

    @Override
    public void update(int position) {
        Log.d("SohelTest","Update Click");

    }

    private String getDoctorName(int doctorId){
        Doctor doctor =dbDoctor.getDoctor(doctorId);

        return doctor.getName();
    }

    private String getCabinName(int cabinId){
        Cabin cabin = dbCabin.getCabin(cabinId);

        return cabin.getName();
    }

    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction().replace(R.id.main_container,new AddPatientFragment())
                .setCustomAnimations(R.anim.enter_from_top,R.anim.exit_to_bottom).addToBackStack(null).commit();
    }
}
