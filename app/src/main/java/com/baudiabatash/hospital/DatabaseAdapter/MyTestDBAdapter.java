package com.baudiabatash.hospital.DatabaseAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.baudiabatash.hospital.Model.Notes;
import com.baudiabatash.hospital.Model.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sohel on 1/7/2017.
 */

public class MyTestDBAdapter {

    private static final String TAG="MyTestDBAdapter";

    // Declare DB Field

    private static final String KEY_ROW_ID="id";
    private static final String KEY_ROW_DATE="date";
    private static final String KEY_ROW_TEST="notes";
    private static final String KEY_ROW_STATUS="status";
    private static final String KEY_ROW_PATIENT_ID="patient_id";



    private static final String[] ALL_KEYS={KEY_ROW_ID,KEY_ROW_DATE,KEY_ROW_TEST,KEY_ROW_STATUS,KEY_ROW_PATIENT_ID};

    // Db Name and Table Name
    private static final String DB_NAME="testDb";
    private static final String TABLE_NAME="testTable";

    // Db Version
    private static final int DATABASE_VERSION=3;

    private static final String DATABASE_CREATE_SQL="create table "+TABLE_NAME
            +"("+KEY_ROW_ID+" integer primary key autoincrement, "
            +KEY_ROW_DATE+" string not null, "
            +KEY_ROW_TEST+" string not null, "
            +KEY_ROW_STATUS+" integer not null, "
            +KEY_ROW_PATIENT_ID+" integer not null"
            +");";


    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public MyTestDBAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public MyTestDBAdapter open(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    // inserting a single Row Data

    private long insertRow(String date,String test,int status,int parent_id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ROW_DATE,date);
        contentValues.put(KEY_ROW_TEST,test);
        contentValues.put(KEY_ROW_STATUS,status);
        contentValues.put(KEY_ROW_PATIENT_ID,parent_id);
        return db.insert(TABLE_NAME,null,contentValues);
    }

    // Insert Direct Object
    public long insertNote(Test test){
        return insertRow(test.getDate(),test.getTest(),test.getStatus(),test.getPatient_id());
    }


    private boolean deleteRow(long rowId){
        String where = KEY_ROW_ID+"="+rowId;
        return db.delete(TABLE_NAME,where,null) != 0;
    }

    private Cursor getAllRows(){
        String where = null;
        Cursor c=db.query(true,TABLE_NAME,ALL_KEYS,where,null,null,null,null,null);

        if(c==null){
            Log.d(TAG,"NULL");
        }

        if(c!= null){
            c.moveToFirst();
        }
        return c;
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


    private Cursor getRow(long rowId){
        String where = KEY_ROW_ID+"="+rowId;
        Cursor c = db.query(true,TABLE_NAME,ALL_KEYS,where,null,null,null,null,null);

        if(c!= null){
            c.moveToFirst();
        }
        return c;
    }

    private Cursor getAllRowForPatient(int patient_id){
        String where =KEY_ROW_PATIENT_ID+"="+patient_id;
        Cursor c = db.query(true,TABLE_NAME,ALL_KEYS,where,null,null,null,null,null);
        if(c!= null){
            c.moveToFirst();
        }
        return c;
    }

    public List<Test> getAllTest(int patient_id){
        List<Test> testList = new ArrayList<>();
        Cursor cursor = getAllRowForPatient(patient_id);

        if(cursor.moveToFirst()){
            do{
                Test test = new Test(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4));
                testList.add(test);

            }while (cursor.moveToNext());
        }

        return testList;
    }




    public boolean updateRow(long id,String date, String test,int status,int parent_id){
        String where= KEY_ROW_ID+"="+id;

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ROW_DATE,date);
        contentValues.put(KEY_ROW_TEST,test);
        contentValues.put(KEY_ROW_STATUS,status);
        contentValues.put(KEY_ROW_PATIENT_ID,parent_id);

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
