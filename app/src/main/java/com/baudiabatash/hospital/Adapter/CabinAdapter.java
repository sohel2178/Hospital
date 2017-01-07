package com.baudiabatash.hospital.Adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baudiabatash.hospital.Model.Cabin;
import com.baudiabatash.hospital.Model.Doctor;
import com.baudiabatash.hospital.R;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

/**
 * Created by Sohel on 1/7/2017.
 */

public class CabinAdapter extends RecyclerView.Adapter<CabinAdapter.CabinViewHolder> {

    private Context context;
    private List<Cabin> cabinList;
    private LayoutInflater inflater;

    public CabinAdapter(Context context, List<Cabin> cabinList) {
        this.context = context;
        this.cabinList = cabinList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CabinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_cabin,parent,false);

        CabinViewHolder holder = new CabinViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CabinViewHolder holder, int position) {

        Cabin cabin = cabinList.get(position);

        int status = cabin.getStatus();

        if(status==1){
            holder.itvStatus.setText(context.getResources().getString(R.string.icon_check));
            holder.itvStatus.setTextColor(ResourcesCompat.getColor(context.getResources(),R.color.color_green,null));
        }else{
            holder.itvStatus.setText(context.getResources().getString(R.string.icon_times));
            holder.itvStatus.setTextColor(ResourcesCompat.getColor(context.getResources(),R.color.color_delete,null));
        }

        holder.tvCabinName.setText(cabin.getName());

    }

    @Override
    public int getItemCount() {
        return cabinList.size();
    }

    public class CabinViewHolder extends RecyclerView.ViewHolder{

        TextView tvCabinName;
        IconTextView itvStatus;

        public CabinViewHolder(View itemView) {
            super(itemView);

            tvCabinName = (TextView) itemView.findViewById(R.id.cabin_name);
            itvStatus = (IconTextView) itemView.findViewById(R.id.status);
        }

    }

}
