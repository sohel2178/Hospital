package com.baudiabatash.hospital.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.baudiabatash.hospital.Model.Cabin;
import com.baudiabatash.hospital.Model.Doctor;

import java.util.List;

/**
 * Created by Sohel on 1/8/2017.
 */

public class CabinSpinnerAdapter extends ArrayAdapter {
    private Context context;
    private List<Cabin> objects;


    public CabinSpinnerAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects =objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(objects.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(objects.get(position).getName());
        return label;
    }
}
