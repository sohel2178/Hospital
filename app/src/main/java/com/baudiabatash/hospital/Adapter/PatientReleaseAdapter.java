package com.baudiabatash.hospital.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baudiabatash.hospital.Model.Patient;
import com.baudiabatash.hospital.R;

import java.util.List;

/**
 * Created by Sohel on 1/10/2017.
 */

public class PatientReleaseAdapter extends RecyclerView.Adapter<PatientReleaseAdapter.PatientHolder>{

    private Context context;
    private List<Patient> patientList;
    private LayoutInflater inflater;

    private UpdateDataFromId listener;



    public PatientReleaseAdapter(Context context, List<Patient> patientList) {
        this.context = context;
        this.patientList = patientList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public PatientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_release_patient,parent,false);

        PatientHolder holder = new PatientHolder(view);

        return holder;
    }

    public void setUpdateDatafromId(UpdateDataFromId listener){
        this.listener=listener;
    }



    @Override
    public void onBindViewHolder(PatientHolder holder, int position) {

        Patient patient = patientList.get(position);

        holder.tvPatientName.setText(patient.getPatient_name());
        holder.tvPatientId.setText(patient.getPatient_id());
        holder.tvGuardianName.setText(patient.getGuardian_name());
        holder.tvContact.setText(patient.getContact());
        holder.tvAdmissionDate.setText(patient.getAdmission_date());
        holder.tvAddress.setText(patient.getAddress());

        if(listener!=null){
            listener.setDoctor(holder.tvReferenceDoctor,patient.getRef_doctor_id());
            listener.setCabin(holder.tvCabin,patient.getAllocated_cabin_id());
        }




    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public void deletePatient(Patient patient){
        int position = patientList.indexOf(patient);
        patientList.remove(position);

        notifyItemRemoved(position);
    }

    public class PatientHolder extends RecyclerView.ViewHolder {

        TextView tvPatientName,tvPatientId,tvGuardianName,tvContact,tvAdmissionDate,tvAddress,tvReferenceDoctor,tvCabin;


        public PatientHolder(View itemView) {
            super(itemView);
            tvPatientId = (TextView) itemView.findViewById(R.id.patient_id);
            tvPatientName = (TextView) itemView.findViewById(R.id.patient_name);
            tvGuardianName = (TextView) itemView.findViewById(R.id.guardian_name);
            tvContact = (TextView) itemView.findViewById(R.id.contact);
            tvAdmissionDate = (TextView) itemView.findViewById(R.id.admission_date);
            tvAddress = (TextView) itemView.findViewById(R.id.address);
            tvReferenceDoctor = (TextView) itemView.findViewById(R.id.ref_doctor);
            tvCabin = (TextView) itemView.findViewById(R.id.cabin_name);

        }


    }

    public interface UpdateDataFromId{
        public void setDoctor(TextView doctorName, int doctorId);
        public void setCabin(TextView cabinName, int cabinId);
    }


}
