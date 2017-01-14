package com.baudiabatash.hospital.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baudiabatash.hospital.Model.Notes;
import com.baudiabatash.hospital.Model.Test;
import com.baudiabatash.hospital.R;

import java.util.List;

/**
 * Created by Sohel on 1/12/2017.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {

    private Context context;
    private List<Test> testList;
    private LayoutInflater inflater;

    public TestAdapter(Context context, List<Test> testList) {
        this.context = context;
        this.testList = testList;
        this.inflater =LayoutInflater.from(context);
    }

    @Override
    public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_test,parent,false);
        TestHolder holder = new TestHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TestHolder holder, int position) {

        Test test = testList.get(position);

        holder.tvNumber.setText(String.valueOf(position+1));
        holder.tvTest.setText(test.getTest());
        holder.tvDate.setText(test.getDate());

    }

    public void insertTest(Test test){
        testList.add(0,test);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class TestHolder extends RecyclerView.ViewHolder{
        TextView tvNumber,tvTest,tvDate;

        public TestHolder(View itemView) {
            super(itemView);
            tvNumber = (TextView) itemView.findViewById(R.id.number);
            tvTest = (TextView) itemView.findViewById(R.id.test);
            tvDate = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
