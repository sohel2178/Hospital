package com.baudiabatash.hospital.Utility;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Sohel on 1/5/2017.
 */

public class MyUtils {

    public static void showDialogAndSetTime(Context context, final EditText editText) {
        final Calendar c = Calendar.getInstance();

        String date = editText.getText().toString().trim();


        if(!date.equals("")){
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            try{
                c.setTime(formatter.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                int actualMonth = monthOfYear+1;
                StringBuffer sb = new StringBuffer();
                String day="";
                String month="";
                if(String.valueOf(dayOfMonth).length()==1){
                    day="0"+dayOfMonth;
                }else{
                    day=String.valueOf(dayOfMonth);
                }
                if(String.valueOf(actualMonth).length()==1){
                    month="0"+(monthOfYear+1);
                }else{
                    month=String.valueOf(monthOfYear+1);
                }
                sb.append(day)
                        .append("-")
                        .append(month)
                        .append("-")
                        .append(year);

                editText.setText(sb.toString());
            }
        },mYear,mMonth,mDay);
        datePickerDialog.show();
        datePickerDialog.setTitle("Pick a Date");
    }
}
