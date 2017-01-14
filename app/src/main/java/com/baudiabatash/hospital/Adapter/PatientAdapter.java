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
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

/**
 * Created by Sohel on 1/10/2017.
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientHolder>{

    private Context context;
    private List<Patient> patientList;
    private LayoutInflater inflater;

    private PatientClickListener patientClickListener;


    public PatientAdapter(Context context, List<Patient> patientList) {
        this.context = context;
        this.patientList = patientList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public PatientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_patient,parent,false);

        PatientHolder holder = new PatientHolder(view);

        return holder;
    }



    public void setPatientClickListener(PatientClickListener patientClickListener){
        this.patientClickListener=patientClickListener;
    }

    @Override
    public void onBindViewHolder(PatientHolder holder, int position) {

        Patient patient = patientList.get(position);

        holder.tvPatientName.setText(patient.getPatient_name());
        holder.tvPatientId.setText(patient.getPatient_id());
        holder.tvContact.setText(patient.getContact());







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

    public class PatientHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvPatientName,tvPatientId,tvContact;
        IconTextView btnUpdate,btnDelete,btnRelease;

        public PatientHolder(View itemView) {
            super(itemView);
            tvPatientId = (TextView) itemView.findViewById(R.id.patient_id);
            tvPatientName = (TextView) itemView.findViewById(R.id.patient_name);

            tvContact = (TextView) itemView.findViewById(R.id.contact);

            btnUpdate = (IconTextView) itemView.findViewById(R.id.update);
            btnDelete = (IconTextView) itemView.findViewById(R.id.delete);
            btnRelease = (IconTextView) itemView.findViewById(R.id.release);

            btnDelete.setOnClickListener(this);
            btnUpdate.setOnClickListener(this);
            btnRelease.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(v==btnUpdate){
                if(patientClickListener!=null){
                    patientClickListener.update(getAdapterPosition());
                }

            }else if(v==btnDelete){
                if(patientClickListener!=null){
                    patientClickListener.delete(getAdapterPosition());
                }

            }else if(v==btnRelease){
                if(patientClickListener!=null){
                    patientClickListener.release(getAdapterPosition());
                }

            }else if(v==itemView){
                if(patientClickListener!=null){
                    patientClickListener.itemClick(getAdapterPosition());
                }

            }

        }
    }



    public interface PatientClickListener{
        public void itemClick(int position);
        public void delete(int position);
        public void release(int position);
        public void update(int position);
    }
}
