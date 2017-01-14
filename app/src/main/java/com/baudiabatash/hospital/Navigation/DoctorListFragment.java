package com.baudiabatash.hospital.Navigation;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
public class DoctorListFragment extends Fragment implements DoctorAdapter.DoctorListener,View.OnClickListener {

    private RecyclerView rvDoctors;
    private FloatingActionButton fabAdd;

    private List<Doctor> doctorList;
    private DoctorAdapter adapter;

    private MyDoctorDBAdapter dbDoctor;

    private ActionBar actionBar;


    public DoctorListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        openDb();
        doctorList = new ArrayList<>();
        doctorList.addAll(dbDoctor.getAllDoctors());
        adapter = new DoctorAdapter(getActivity(),doctorList);

        adapter.setDoctorListener(this);

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

        fabAdd = (FloatingActionButton) view.findViewById(R.id.add);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fabAdd.setOnClickListener(this);
    }

    @Override
    public void updateItem(int position) {

    }

    @Override
    public void onResume() {
        super.onResume();

        actionBar.setTitle("Doctors List");

    }

    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction().replace(R.id.main_container,new AddDoctorFragment())
                .setCustomAnimations(R.anim.enter_from_top,R.anim.exit_to_bottom).addToBackStack(null).commit();
    }
}
