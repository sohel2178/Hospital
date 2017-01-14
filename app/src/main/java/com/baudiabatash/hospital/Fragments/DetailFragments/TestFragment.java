package com.baudiabatash.hospital.Fragments.DetailFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baudiabatash.hospital.Adapter.TestAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyTestDBAdapter;
import com.baudiabatash.hospital.Interface.FragmentDetector;
import com.baudiabatash.hospital.Model.Notes;
import com.baudiabatash.hospital.Model.Test;
import com.baudiabatash.hospital.R;
import com.baudiabatash.hospital.Utility.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    private FragmentDetector detector;

    private RecyclerView rvTest;
    private List<Test> testList;
    private TestAdapter adapter;

    private MyTestDBAdapter dbTest;

    private int patient_id;


    public TestFragment() {
        // Required empty public constructor
    }

    public void setFragmentDetector(FragmentDetector detector){
        this.detector=detector;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
            patient_id = getArguments().getInt(Constant.PATIENT_ID);
        }

        openDb();

        testList = new ArrayList<>();
        testList.addAll(dbTest.getAllTest(patient_id));
        Collections.reverse(testList);

        adapter = new TestAdapter(getActivity(),testList);
    }

    private void openDb() {
        dbTest = new MyTestDBAdapter(getActivity());
        dbTest.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        rvTest = (RecyclerView) view.findViewById(R.id.rv_test);
        rvTest.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTest.setAdapter(adapter);
    }


    public void updateAdapter(Test test){
        adapter.insertTest(test);
    }


    @Override
    public void onResume() {
        super.onResume();

        if(detector!=null){
            detector.detect(2);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbTest.close();
    }
}
