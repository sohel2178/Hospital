package com.baudiabatash.hospital.Navigation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baudiabatash.hospital.CustomView.MyEditText;
import com.baudiabatash.hospital.DatabaseAdapter.MyCabinDBAdapter;
import com.baudiabatash.hospital.Model.Cabin;
import com.baudiabatash.hospital.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCabinFragment extends DialogFragment implements View.OnClickListener{

    private TextView tvTitle;
    private MyEditText etCabinName;
    private Button btnCancel,btnAdd;

    // Decale Cabin Database
    MyCabinDBAdapter dbCabin;



    public AddCabinFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openCabinDatabase();
    }

    private void openCabinDatabase() {
        dbCabin = new MyCabinDBAdapter(getActivity());
        dbCabin.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_cabin, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.title);
        etCabinName = (MyEditText) view.findViewById(R.id.cabin_name);
        btnCancel = (Button) view.findViewById(R.id.cancel);
        btnAdd = (Button) view.findViewById(R.id.add);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:

                String cabinName = etCabinName.getText().toString().trim();

                if(TextUtils.isEmpty(cabinName)){
                    etCabinName.requestFocus();
                    Toast.makeText(getActivity(), "Cabin Name is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cabin cabin = new Cabin(cabinName);

                if(dbCabin.insertCabin(cabin)>0){
                    Toast.makeText(getActivity(), "Cabin Added Successfully to Database", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }

                break;

            case R.id.cancel:
                getDialog().dismiss();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbCabin.close();
    }
}
