package com.baudiabatash.hospital.DatabaseAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.baudiabatash.hospital.Model.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sohel on 1/7/2017.
 */

public class MyReleasePatientDBAdapter {

    private static final String TAG="MyReleasePatientDBAdapter";

    // Declare DB Field

    private static final String KEY_ROW_ID="id";
    private static final String KEY_ROW_PATIENT_ID="patient_id";
    private static final String KEY_ROW_ADMISSION_DATE="admission_date";
    private static final String KEY_ROW_PATIENT_NAME="patient_name";
    private static final String KEY_ROW_GUARDIAN_NAME="guardian_name";
    private static final String KEY_ROW_CONTACT="contact";
    private static final String KEY_ROW_ADDRESS="address";
    private static final String KEY_ROW_REF_DOCTOR_ID="ref_doctor_id";
    private static final String KEY_ROW_ALLOCATED_CABIN_ID="allocated_cabin_id";


    private static final String[] ALL_KEYS={KEY_ROW_ID,KEY_ROW_PATIENT_ID,KEY_ROW_ADMISSION_DATE,KEY_ROW_PATIENT_NAME,KEY_ROW_GUARDIAN_NAME,
            KEY_ROW_CONTACT,KEY_ROW_ADDRESS, KEY_ROW_REF_DOCTOR_ID,KEY_ROW_ALLOCATED_CABIN_ID};

    // Db Name and Table Name
    private static final String DB_NAME="releasePatientDb";
    private static final String TABLE_NAME="releasePatientTable";

    // Db Version
    private static final int DATABASE_VERSION=1;

    private static final String DATABASE_CREATE_SQL="create table "+TABLE_NAME
            +"("+KEY_ROW_ID+" integer primary key autoincrement, "
            +KEY_ROW_PATIENT_ID+" string not null, "
            +KEY_ROW_ADMISSION_DATE+" string not null, "
            +KEY_ROW_PATIENT_NAME+" string not null, "
            +KEY_ROW_GUARDIAN_NAME+" string not null, "
            +KEY_ROW_CONTACT+" string not null, "
            +KEY_ROW_ADDRESS+" string, "
            +KEY_ROW_REF_DOCTOR_ID+" integer not null, "
            +KEY_ROW_ALLOCATED_CABIN_ID+" integer not null "
            +");";


    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public MyReleasePatientDBAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public MyReleasePatientDBAdapter open(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    // inserting a single Row Data

    public long insertRow(String patient_id,String date,String patient_name,String guardian,String contact, String address,
                          int ref_doctor_id,int allocated_cabin_id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ROW_PATIENT_ID,patient_id);
        contentValues.put(KEY_ROW_ADMISSION_DATE,date);
        contentValues.put(KEY_ROW_PATIENT_NAME,patient_name);
        contentValues.put(KEY_ROW_GUARDIAN_NAME,guardian);
        contentValues.put(KEY_ROW_CONTACT,contact);
        contentValues.put(KEY_ROW_ADDRESS,address);
        contentValues.put(KEY_ROW_REF_DOCTOR_ID,ref_doctor_id);
        contentValues.put(KEY_ROW_ALLOCATED_CABIN_ID,allocated_cabin_id);

        return db.insert(TABLE_NAME,null,contentValues);
    }

    // Insert Direct Object
    public long insertPatient(Patient patient){
        return insertRow(patient.getPatient_id(),patient.getAdmission_date(),patient.getPatient_name(),patient.getGuardian_name(),
               patient.getContact(), patient.getAddress(),patient.getRef_doctor_id(),patient.getAllocated_cabin_id());
    }

    public boolean deletePatient(int id){
        return deleteRow(id);
    }

    public boolean deletePatient(Patient patient){
        return deletePatient(patient.getId());
    }

    private boolean deleteRow(long rowId){
        String where = KEY_ROW_ID+"="+rowId;
        return db.delete(TABLE_NAME,where,null) != 0;
    }

    private Cursor getAllRows(){
        String where = null;
        Cursor c=db.query(true,TABLE_NAME,ALL_KEYS,where,null,null,null,null,null);

        if(c!= null){
            c.moveToFirst();
        }
        return c;
    }

    public List<Patient> getAllPatient(){

        List<Patient> patientList = new ArrayList<>();

        Cursor cursor = getAllRows();

        if(cursor.moveToFirst()){
            do{
                Patient patient = new Patient(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(7),cursor.getInt(8));

                patientList.add(patient);

            }while (cursor.moveToNext());
        }

        return patientList;

    }

    public void deleteAll(){

        Cursor c = getAllRows();

        int rowId = c.getColumnIndexOrThrow(KEY_ROW_ID);

        if(c.moveToFirst()){
            do{
                deleteRow(c.getLong(rowId));
            }while (c.moveToNext());
        }

    }


    public Cursor getRow(long rowId){
        String where = KEY_ROW_ID+"="+rowId;
        Cursor c = db.query(true,TABLE_NAME,ALL_KEYS,where,null,null,null,null,null);

        if(c!= null){
            c.moveToFirst();
        }
        return c;
    }


    /*public boolean updateRow(long id,String name, String designation,String degree, String organization){
        String where= KEY_ROW_ID+"="+id;

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ROW_NAME,name);
        contentValues.put(KEY_ROW_DESIGNATION,designation);
        contentValues.put(KEY_ROW_DEGREE,degree);
        contentValues.put(KEY_ROW_ORGANIZATION,organization);

        return db.update(TABLE_NAME,contentValues,where,null) !=0;
    }*/



    private static class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            // Destroy old database:
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            // Recreate new database:
            onCreate(db);

        }
    }


}
