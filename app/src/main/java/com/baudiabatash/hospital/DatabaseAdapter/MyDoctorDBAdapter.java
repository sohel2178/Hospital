package com.baudiabatash.hospital.DatabaseAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.baudiabatash.hospital.Model.Doctor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sohel on 1/7/2017.
 */

public class MyDoctorDBAdapter {

    private static final String TAG="MyDoctorDBAdapter";

    // Declare DB Field

    private static final String KEY_ROW_ID="id";
    private static final String KEY_ROW_NAME="name";
    private static final String KEY_ROW_DESIGNATION="designation";
    private static final String KEY_ROW_CONTACT="contact";
    private static final String KEY_ROW_DEGREE="degree";
    private static final String KEY_ROW_ORGANIZATION="organization";

    // All Field Number
    private static final int COL_ROW_ID=0;
    private static final int COL_ROW_NAME=1;
    private static final int COL_ROW_DESIGNATION=2;
    private static final int COL_ROW_DEGREE=3;
    private static final int COL_ROW_ORGANIZATION=4;

    private static final String[] ALL_KEYS={KEY_ROW_ID,KEY_ROW_NAME,KEY_ROW_DESIGNATION,KEY_ROW_CONTACT,KEY_ROW_DEGREE,KEY_ROW_ORGANIZATION};

    // Db Name and Table Name
    private static final String DB_NAME="doctorsDb";
    private static final String TABLE_NAME="doctorsTable";

    // Db Version
    private static final int DATABASE_VERSION=2;

    private static final String DATABASE_CREATE_SQL="create table "+TABLE_NAME
            +"("+KEY_ROW_ID+" integer primary key autoincrement, "
            +KEY_ROW_NAME+" string not null, "
            +KEY_ROW_DESIGNATION+" string not null, "
            +KEY_ROW_CONTACT+" string not null, "
            +KEY_ROW_DEGREE+" string not null, "
            +KEY_ROW_ORGANIZATION+" string not null"
            +");";


    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public MyDoctorDBAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public MyDoctorDBAdapter open(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    // inserting a single Row Data

    public long insertRow(String name,String designation,String contact,String degree, String organization){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ROW_NAME,name);
        contentValues.put(KEY_ROW_DESIGNATION,designation);
        contentValues.put(KEY_ROW_CONTACT,contact);
        contentValues.put(KEY_ROW_DEGREE,degree);
        contentValues.put(KEY_ROW_ORGANIZATION,organization);

        return db.insert(TABLE_NAME,null,contentValues);
    }

    // Insert Direct Object
    public long insertRow(Doctor doctor){
        return insertRow(doctor.getName(),doctor.getDesignation(),doctor.getContact(),doctor.getDegree(),doctor.getOrganization());
    }

    public boolean deleteDoctor(int id){
        return deleteRow(id);
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

    public List<Doctor> getAllDoctors(){

        List<Doctor> doctorList = new ArrayList<>();

        Cursor cursor = getAllRows();

        if(cursor.moveToFirst()){
            do{
                Doctor doctor = new Doctor(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
                doctorList.add(doctor);

            }while (cursor.moveToNext());
        }

        return doctorList;

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


    public Doctor getDoctor(int id){
        List<Doctor> doctorList = new ArrayList<>();

        Cursor cursor = getRow(id);

        if(cursor.moveToFirst()){
            do{
                Doctor doctor = new Doctor(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
                doctorList.add(doctor);

            }while (cursor.moveToNext());
        }

        return doctorList.get(0);


    }


    public Cursor getRow(long rowId){
        String where = KEY_ROW_ID+"="+rowId;
        Cursor c = db.query(true,TABLE_NAME,ALL_KEYS,where,null,null,null,null,null);

        if(c!= null){
            c.moveToFirst();
        }
        return c;
    }


    public boolean updateRow(long id,String name, String designation,String contact,String degree, String organization){
        String where= KEY_ROW_ID+"="+id;

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ROW_NAME,name);
        contentValues.put(KEY_ROW_DESIGNATION,designation);
        contentValues.put(KEY_ROW_CONTACT,contact);
        contentValues.put(KEY_ROW_DEGREE,degree);
        contentValues.put(KEY_ROW_ORGANIZATION,organization);

        return db.update(TABLE_NAME,contentValues,where,null) !=0;
    }



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
