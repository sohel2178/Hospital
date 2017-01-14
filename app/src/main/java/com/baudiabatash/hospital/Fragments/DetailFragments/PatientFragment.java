package com.baudiabatash.hospital.Fragments.DetailFragments;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baudiabatash.hospital.DialogFragment.AddNotesFragment;
import com.baudiabatash.hospital.DialogFragment.AddTestFragment;
import com.baudiabatash.hospital.Interface.FragmentDetector;
import com.baudiabatash.hospital.Model.Notes;
import com.baudiabatash.hospital.Model.Patient;
import com.baudiabatash.hospital.Model.Test;
import com.baudiabatash.hospital.R;
import com.baudiabatash.hospital.Utility.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends Fragment implements View.OnClickListener,FragmentDetector{

    private ActionBar actionBar;

    private static final int REQUEST_CODE_NOTES=1;
    private static final int REQUEST_CODE_TEST=2;
    TextView tvPatientName,tvPatientId,tvGuardianName,tvAdmissionDate,tvAddress,tvReferenceDoctor,tvCabin;

    private LinearLayout infoContainer;

    private TextView tvNotes,tvTest;

    private Button btnAddNotes,btnAddTest;

    private ImageView ivUpDown;

    private Patient patient;

    private String doctorName,cabinName;

    private NotesFragment notesFragment;
    private TestFragment testFragment;

    private int patient_id;

    private Bundle bundle;

    private int fragment_no;

    private int upDownIndicator;


    public PatientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(getArguments()!=null){
            patient = (Patient) getArguments().getSerializable(Constant.PATIENT);
            patient_id = patient.getId();
            doctorName = getArguments().getString(Constant.DOCTOR_NAME);
            cabinName = getArguments().getString(Constant.CABIN_NAME);
        }

        upDownIndicator =0;

        bundle = new Bundle();
        bundle.putInt(Constant.PATIENT_ID,patient_id);

        notesFragment = new NotesFragment();
        testFragment = new TestFragment();

        // Add Notes fragment
        transactNote();

        fragment_no=1;



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient, container, false);

        initView(view);

        // init Bold Notes
        boldNotes();

        if(getArguments()!= null){
            tvPatientName.setText(patient.getPatient_name());
            tvPatientId.setText(patient.getPatient_id());
            tvGuardianName.setText(patient.getGuardian_name());
            tvAdmissionDate.setText(patient.getAdmission_date());
            tvAddress.setText(patient.getAddress());
            tvReferenceDoctor.setText(doctorName);
            tvCabin.setText(cabinName);


        }

        return view;
    }

    private void initView(View view) {
        tvPatientId = (TextView) view.findViewById(R.id.patient_id);
        tvPatientName = (TextView) view.findViewById(R.id.patient_name);
        tvGuardianName = (TextView) view.findViewById(R.id.guardian_name);
        tvAdmissionDate = (TextView) view.findViewById(R.id.admission_date);
        tvAddress = (TextView) view.findViewById(R.id.address);
        tvReferenceDoctor = (TextView) view.findViewById(R.id.ref_doctor);
        tvCabin = (TextView) view.findViewById(R.id.cabin_name);

        tvTest = (TextView) view.findViewById(R.id.test);
        tvNotes = (TextView) view.findViewById(R.id.note);

        btnAddNotes = (Button) view.findViewById(R.id.add_notes);
        btnAddTest = (Button) view.findViewById(R.id.add_test);

        ivUpDown = (ImageView) view.findViewById(R.id.up_down);

        infoContainer = (LinearLayout) view.findViewById(R.id.info_container);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnAddNotes.setOnClickListener(this);
        btnAddTest.setOnClickListener(this);

        tvNotes.setOnClickListener(this);
        tvTest.setOnClickListener(this);

        ivUpDown.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_notes:
                AddNotesFragment addNotesFragment = new AddNotesFragment();
                addNotesFragment.setArguments(bundle);
                addNotesFragment.setTargetFragment(this,REQUEST_CODE_NOTES);
                addNotesFragment.show(getFragmentManager(),Constant.DEFAULAT_FRAGMENT_TAG);
                break;

            case R.id.add_test:
                AddTestFragment addTestFragment = new AddTestFragment();
                addTestFragment.setArguments(bundle);
                addTestFragment.setTargetFragment(this,REQUEST_CODE_TEST);
                addTestFragment.show(getFragmentManager(),Constant.DEFAULAT_FRAGMENT_TAG);
                break;

            case R.id.note:
                if(fragment_no!=1){
                    transactNote();
                }

                break;

            case R.id.test:
                if(fragment_no!=2){
                    transactTest();
                }

                break;


            case R.id.up_down:
                upDownIndicator++;

                if(upDownIndicator%2==0){
                    down();
                }else{
                    up();
                }
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        actionBar.setTitle(patient.getPatient_name());
    }

    private void up(){
        ivUpDown.setImageResource(R.drawable.up);
        animationDown(infoContainer);

    }

    private void down(){
       infoContainer.setVisibility(View.VISIBLE);
        ivUpDown.setImageResource(R.drawable.down);

        animationUp(infoContainer);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE_NOTES:
                if(resultCode== Activity.RESULT_OK){
                    Bundle bundle =data.getExtras();
                    String date = bundle.getString("date");
                    String notes = bundle.getString("notes");

                    Notes note = new Notes(date,notes,patient_id);
                    notesFragment.updateAdapter(note);

                }
                break;

            case REQUEST_CODE_TEST:
                Bundle bundle =data.getExtras();
                String date = bundle.getString("date");
                String testStr = bundle.getString("test");

                Log.d("Sohel",date);

                Test test = new Test(date,testStr,0,patient_id);
                testFragment.updateAdapter(test);
                break;
        }
    }

    @Override
    public void detect(int fragmentNo) {

        // Update Fragmen Number Here
        this.fragment_no=fragmentNo;

        normalTextSize();

        switch (fragmentNo){
            case 1:
                boldNotes();
                btnAddTest.setVisibility(View.GONE);
                btnAddNotes.setVisibility(View.VISIBLE);
                break;

            case 2:
                boldTest();
                btnAddNotes.setVisibility(View.GONE);
                btnAddTest.setVisibility(View.VISIBLE);
                break;
        }
    }


    private void transactNote(){
        notesFragment.setFragmentDetector(this);
        notesFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.test_note_container,notesFragment).commit();
    }


    private void transactTest(){
        testFragment.setFragmentDetector(this);
        testFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.test_note_container,testFragment).commit();
    }


    private void normalTextSize(){
        tvTest.setTextSize(14);
        tvNotes.setTextSize(14);
        tvTest.setTypeface(Typeface.DEFAULT);
        tvNotes.setTypeface(Typeface.DEFAULT);
    }

    private void boldTest(){
        tvTest.setTextSize(18);
        tvTest.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private void boldNotes(){
        tvNotes.setTextSize(18);
        tvNotes.setTypeface(Typeface.DEFAULT_BOLD);
    }


    private   void animationUp(final View view){

        AnimatorSet animatorSet = new AnimatorSet();


        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view,"scaleY",0,1);
        //ObjectAnimator translateX = ObjectAnimator.ofFloat(holder.itemView,"translationX",width,0);
        scaleY.setDuration(1500);
        // translateX.setDuration(1500);
        animatorSet.playTogether(scaleY);

        animatorSet.start();

        scaleY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }

    private   void animationDown(final View view){

        AnimatorSet animatorSet = new AnimatorSet();


        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view,"scaleY",1,0);
        //ObjectAnimator translateX = ObjectAnimator.ofFloat(holder.itemView,"translationX",width,0);
        scaleY.setDuration(1500);
        // translateX.setDuration(1500);
        animatorSet.playTogether(scaleY);

        animatorSet.start();

        scaleY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }
}
