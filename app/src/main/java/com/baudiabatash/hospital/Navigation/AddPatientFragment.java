package com.baudiabatash.hospital.Navigation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baudiabatash.hospital.Adapter.CabinSpinnerAdapter;
import com.baudiabatash.hospital.Adapter.DoctorSpinnerAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyCabinDBAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyDoctorDBAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyPatientDBAdapter;
import com.baudiabatash.hospital.Model.Cabin;
import com.baudiabatash.hospital.Model.Doctor;
import com.baudiabatash.hospital.Model.Patient;
import com.baudiabatash.hospital.R;
import com.baudiabatash.hospital.Utility.MyUtils;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPatientFragment extends Fragment implements View.OnClickListener{
    private ActionBar actionBar;

    private EditText etDate,etPatientId,etPatientName,etGuardianName,etContact,etAddress;
    private IconTextView calendar;
    private TextView tvTitle;
    private Spinner spDoctors,spCabins;
    private Button btnAdd;

    private MyCabinDBAdapter dbCabin;
    private MyDoctorDBAdapter dbDoctor;

    private MyPatientDBAdapter dbPatient;

    private List<Cabin> cabinList;
    private List<Doctor> doctorList;

    DoctorSpinnerAdapter doctorAdapter;
    CabinSpinnerAdapter cabinAdapter;




    public AddPatientFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        openDatabase();

        cabinList = new ArrayList<>();
        doctorList = new ArrayList<>();

        doctorList.addAll(dbDoctor.getAllDoctors());
        doctorAdapter = new DoctorSpinnerAdapter(getActivity(),R.layout.single_doctor_spinner,doctorList);

        cabinList.addAll(dbCabin.getAllEmptyCabin());
        cabinAdapter= new CabinSpinnerAdapter(getActivity(),R.layout.single_doctor_spinner,cabinList);

    }

    private void openDatabase() {
        dbCabin = new MyCabinDBAdapter(getActivity());
        dbDoctor = new MyDoctorDBAdapter(getActivity());
        dbPatient = new MyPatientDBAdapter(getActivity());

        dbCabin.open();
        dbDoctor.open();
        dbPatient.open();
    }

    @Override
    public void onResume() {
        super.onResume();
        actionBar.setTitle("Add Patient");
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
        tvTitle = (TextView) view.findViewById(R.id.title);
        etDate = (EditText) view.findViewById(R.id.date);
        etPatientId = (EditText) view.findViewById(R.id.patient_id);
        etPatientName = (EditText) view.findViewById(R.id.patient_name);
        etGuardianName = (EditText) view.findViewById(R.id.guardian_name);
        etContact = (EditText) view.findViewById(R.id.contact);
        etAddress = (EditText) view.findViewById(R.id.address);
        spDoctors = (Spinner) view.findViewById(R.id.sp_ref_doctor);
        spCabins = (Spinner) view.findViewById(R.id.sp_cabin);
        calendar = (IconTextView) view.findViewById(R.id.calendar);
        btnAdd = (Button) view.findViewById(R.id.add);

        spDoctors.setAdapter(doctorAdapter);
        spCabins.setAdapter(cabinAdapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        calendar.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.calendar:
                MyUtils.showDialogAndSetTime(getActivity(),etDate);
                break;

            case R.id.add:

                MyUtils.hideKey(v);

                String date = etDate.getText().toString().trim();
                String patient_id = etPatientId.getText().toString().trim();
                String patient_name = etPatientName.getText().toString().trim();
                String guardian = etGuardianName.getText().toString().trim();
                String contact = etContact.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                Doctor doctor = (Doctor) spDoctors.getSelectedItem();
                Cabin cabin = (Cabin) spCabins.getSelectedItem();

                if(TextUtils.isEmpty(date)){
                    etDate.requestFocus();
                    Toast.makeText(getActivity(), "Please Select a date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(patient_id)){
                    etPatientId.requestFocus();
                    Toast.makeText(getActivity(), "Patient Id is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(patient_name)){
                    etPatientName.requestFocus();
                    Toast.makeText(getActivity(), "Patient Name is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(guardian)){
                    etGuardianName.requestFocus();
                    Toast.makeText(getActivity(), "Guardian Name is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(doctor==null){
                    return;
                }

                if(cabin==null){
                    return;
                }

                Patient patient = new Patient(patient_id,date,patient_name,guardian,contact,address,doctor.getId(),cabin.getId());

                if(dbPatient.insertPatient(patient)>0){

                    // Update Cabin
                    cabin.setStatus(1);
                    dbCabin.updateCabin(cabin);

                    getFragmentManager().popBackStack();

                    getFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment())
                            .setCustomAnimations(R.anim.enter_from_top,R.anim.exit_to_bottom).commit();
                }

                //addPatient(patient_id,date,patient_name,guardian,address,doctor.getId(),cabin.getId());

                Log.d("TEST",doctor.getName());
                break;


        }



    }

    //private void addPatient(String patient_id,)

    @Override
    public void onDestroy() {
        super.onDestroy();

        closeDatabase();

    }

    private void closeDatabase() {
        dbCabin.close();
        dbDoctor.close();
        dbPatient.close();
    }
}
