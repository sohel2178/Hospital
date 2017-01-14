package com.baudiabatash.hospital.Fragments.DetailFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baudiabatash.hospital.Adapter.NotesAdapter;
import com.baudiabatash.hospital.DatabaseAdapter.MyNotesDBAdapter;
import com.baudiabatash.hospital.Interface.FragmentDetector;
import com.baudiabatash.hospital.Model.Notes;
import com.baudiabatash.hospital.R;
import com.baudiabatash.hospital.Utility.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {

    private FragmentDetector detector;

    private RecyclerView rvNotes;

    private MyNotesDBAdapter dbNotes;
    private List<Notes> notesList;
    private NotesAdapter adapter;

    private int patient_id;


    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
            patient_id = getArguments().getInt(Constant.PATIENT_ID);
        }

        openDb();
        notesList = new ArrayList<>();
        notesList.addAll(dbNotes.getAllNotes(patient_id));
        Collections.reverse(notesList);
        adapter = new NotesAdapter(getActivity(),notesList);
    }

    private void openDb() {
        dbNotes = new MyNotesDBAdapter(getActivity());
        dbNotes.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        
        initView(view);
        
        return view;
    }

    public void setFragmentDetector(FragmentDetector detector){
        this.detector=detector;
    }

    private void initView(View view) {
        rvNotes = (RecyclerView) view.findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNotes.setAdapter(adapter);

    }

    public void updateAdapter(Notes note){
        adapter.insertNotes(note);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(detector!= null){
            detector.detect(1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbNotes.close();
    }
}
