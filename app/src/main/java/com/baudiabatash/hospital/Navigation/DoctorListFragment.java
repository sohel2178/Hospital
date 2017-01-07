package com.baudiabatash.hospital.Navigation;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baudiabatash.hospital.Adapter.DoctorAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyDoctorDBAdapter;
import com.baudiabatash.hospital.Model.Doctor;
import com.baudiabatash.hospital.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorListFragment extends Fragment {

    private RecyclerView rvDoctors;

    private List<Doctor> doctorList;
    private DoctorAdapter adapter;

    private MyDoctorDBAdapter dbDoctor;


    public DoctorListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openDb();
        doctorList = new ArrayList<>();
        adapter = new DoctorAdapter(getActivity(),doctorList);

        getAllDoctors();
    }

    private void getAllDoctors() {
        Cursor cursor = dbDoctor.getAllRows();

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String designation = cursor.getString(2);
                Log.d("TEST",designation);
                String degree = cursor.getString(3);
                String organization = cursor.getString(4);

                Doctor doctor = new Doctor(id,name,designation,degree,organization);

                doctorList.add(doctor);


            }while (cursor.moveToNext());
        }

        adapter.notifyDataSetChanged();
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
        View view= inflater.inflate(R.layout.fragment_doctor_list, container, false);
        
        initView(view);
        
        return view;
    }

    private void initView(View view) {
        rvDoctors = (RecyclerView) view.findViewById(R.id.rv_doctors);
        rvDoctors.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDoctors.setAdapter(adapter);
    }


}
