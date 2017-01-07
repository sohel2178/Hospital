package com.baudiabatash.hospital.Navigation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baudiabatash.hospital.Adapter.CabinAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyCabinDBAdapter;
import com.baudiabatash.hospital.Model.Cabin;
import com.baudiabatash.hospital.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CabinListFragment extends Fragment {

    private RecyclerView rvCabins;

    private List<Cabin> cabinList;
    private CabinAdapter adapter;

    // Cabin Database Declare Here
    private MyCabinDBAdapter dbCabin;


    public CabinListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        dbCabin.close();
    }
}
