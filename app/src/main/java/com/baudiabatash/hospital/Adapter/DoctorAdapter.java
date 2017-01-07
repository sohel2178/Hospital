package com.baudiabatash.hospital.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baudiabatash.hospital.Model.Doctor;
import com.baudiabatash.hospital.R;

import java.util.List;

/**
 * Created by Sohel on 1/7/2017.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private Context context;
    private List<Doctor> doctorList;
    private LayoutInflater inflater;
    private DoctorListener listener;


    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
        this.inflater = LayoutInflater.from(context);
    }

    public void setDoctorListener(DoctorListener listener){
        this.listener=listener;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.single_doctor,parent,false);
        DoctorViewHolder holder = new DoctorViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder holder, int position) {

        Doctor doctor = doctorList.get(position);

        holder.tvName.setText(doctor.getName());
        holder.tvDesignation.setText(doctor.getDesignation());
        holder.tvDegree.setText(doctor.getDegree());
        holder.tvOrganization.setText(doctor.getOrganization());

    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvName,tvDesignation,tvDegree,tvOrganization;
        Button btnDelete,btnUpdate;

        public DoctorViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.name);
            tvDesignation = (TextView) itemView.findViewById(R.id.designation);
            tvDegree = (TextView) itemView.findViewById(R.id.degree);
            tvOrganization = (TextView) itemView.findViewById(R.id.organization);

            btnDelete = (Button) itemView.findViewById(R.id.delete);
            btnUpdate = (Button) itemView.findViewById(R.id.update);

            btnDelete.setOnClickListener(this);
            btnUpdate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.update:
                    if(listener!= null){
                        listener.updateItem(getAdapterPosition());
                    }
                    break;

                case R.id.delete:
                    if(listener!= null){
                        listener.deleteItem(getAdapterPosition());
                    }
                    break;
            }

        }
    }

    public interface DoctorListener{
        public void updateItem(int position);
        public void deleteItem(int position);
    }


    public void removeItem(Doctor doctor){
        int position = doctorList.indexOf(doctor);
        doctorList.remove(position);
        notifyItemRemoved(position);

    }
}
