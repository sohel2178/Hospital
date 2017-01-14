package com.baudiabatash.hospital.Navigation;


import android.app.Activity;
import android.content.Intent;
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
import android.widget.Button;

import com.baudiabatash.hospital.Adapter.CabinAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyCabinDBAdapter;
import com.baudiabatash.hospital.Model.Cabin;
import com.baudiabatash.hospital.R;
import com.baudiabatash.hospital.Utility.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CabinListFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_CODE=12;

    private RecyclerView rvCabins;
    private FloatingActionButton fabAdd;

    private List<Cabin> cabinList;
    private CabinAdapter adapter;

    // Cabin Database Declare Here
    private MyCabinDBAdapter dbCabin;

    private ActionBar actionBar;


    public CabinListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        openCabinDb();
        cabinList = new ArrayList<>();
        // Get All Cabin From Database and Add to the Cabin List
        cabinList.addAll(dbCabin.getAllCabin());
        adapter = new CabinAdapter(getActivity(),cabinList);
    }

    private void openCabinDb() {
        dbCabin = new MyCabinDBAdapter(getActivity());
        dbCabin.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cabin_list, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        rvCabins = (RecyclerView) view.findViewById(R.id.rvCabins);

        rvCabins.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCabins.setAdapter(adapter);

        fabAdd = (FloatingActionButton) view.findViewById(R.id.add);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fabAdd.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbCabin.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        actionBar.setTitle("Cabin List");
    }

    @Override
    public void onClick(View v) {

        AddCabinFragment addCabinFragment = new AddCabinFragment();
        addCabinFragment.show(getFragmentManager(), Constant.DEFAULAT_FRAGMENT_TAG);
        addCabinFragment.setTargetFragment(this,REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE:

                if(resultCode== Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String cabinName = bundle.getString("cabinName");

                    Cabin cabin = new Cabin(cabinName);
                    adapter.addCabin(cabin);
                }

                break;
        }
    }
}
