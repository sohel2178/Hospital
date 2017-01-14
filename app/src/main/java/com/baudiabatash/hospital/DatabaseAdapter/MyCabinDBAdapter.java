package com.baudiabatash.hospital.DatabaseAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.baudiabatash.hospital.Model.Cabin;
import com.baudiabatash.hospital.Model.Doctor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sohel on 1/7/2017.
 */

public class MyCabinDBAdapter {

    private static final String TAG="MyCabinDBAdapter";

    // Declare DB Field

    private static final String KEY_ROW_ID="id";
    private static final String KEY_ROW_NAME="name";
    private static final String KEY_ROW_ALLOCATE_PATIENT_ID="allocate_patient_id";
    private static final String KEY_ROW_STATUS="status";

    // All Field Number
    private static final int COL_ROW_ID=0;
    private static final int COL_ROW_NAME=1;
    private static final int COL_ROW_DESIGNATION=2;
    private static final int COL_ROW_DEGREE=3;
    private static final int COL_ROW_ORGANIZATION=4;

    private static final String[] ALL_KEYS={KEY_ROW_ID,KEY_ROW_NAME,KEY_ROW_ALLOCATE_PATIENT_ID,KEY_ROW_STATUS};

    // Db Name and Table Name
    private static final String DB_NAME="cabinBd";
    private static final String TABLE_NAME="cabinTable";

    // Db Version
    private static final int DATABASE_VERSION=2;

    private static final String DATABASE_CREATE_SQL="create table "+TABLE_NAME
            +"("+KEY_ROW_ID+" integer primary key autoincrement, "
            +KEY_ROW_NAME+" string not null, "
            +KEY_ROW_ALLOCATE_PATIENT_ID+" integer , "
            +KEY_ROW_STATUS+" integer"
            +");";


    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public MyCabinDBAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public MyCabinDBAdapter open(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    // inserting a single Row Data

    public long insertRow(String name,int allocated_patient_id,int status){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ROW_NAME,name);
        contentValues.put(KEY_ROW_ALLOCATE_PATIENT_ID,allocated_patient_id);
        contentValues.put(KEY_ROW_STATUS,status);

        return db.insert(TABLE_NAME,null,contentValues);
    }

    // Insert Direct Object
    public long insertCabin(Cabin cabin){
        return insertRow(cabin.getName(),cabin.getAllocated_patient_id(),cabin.getStatus());
    }

    public List<Cabin> getAllCabin(){
        List<Cabin> cabinList = new ArrayList<>();
        Cursor cursor = getAllRows();

        if(cursor.moveToFirst()){
            do{
                Cabin cabin = new Cabin(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
                cabinList.add(cabin);

            }while (cursor.moveToNext());
        }

        return cabinList;
    }

    public List<Cabin> getAllEmptyCabin(){
        List<Cabin> emptyCabinList = new ArrayList<>();

        Cursor cursor = getAllEmptyRows();

        if(cursor.moveToFirst()){
            do{
                Cabin cabin = new Cabin(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
                emptyCabinList.add(cabin);

            }while (cursor.moveToNext());
        }

        return emptyCabinList;


    }

    public boolean updateCabin(Cabin cabin){
        return updateRow(cabin.getId(),cabin.getName(),cabin.getAllocated_patient_id(),cabin.getStatus());
    }

    public boolean deleteCabin(int id){
        return deleteRow(id);
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

    private Cursor getAllEmptyRows(){
        String where = KEY_ROW_STATUS+"="+0;
        Cursor c=db.query(true,TABLE_NAME,ALL_KEYS,where,null,null,null,null,null);

        if(c!=null){
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

    public Cabin getCabin(int id){
        List<Cabin> cabinList = new ArrayList<>();

        Cursor cursor = getRow(id);

        if(cursor.moveToFirst()){
            do{
                Cabin cabin = new Cabin(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
                cabinList.add(cabin);

            }while (cursor.moveToNext());
        }

        return cabinList.get(0);

    }


    public boolean updateRow(long id,String name, int allocated_patient_id,int status){
        String where= KEY_ROW_ID+"="+id;

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ROW_NAME,name);
        contentValues.put(KEY_ROW_ALLOCATE_PATIENT_ID,allocated_patient_id);
        contentValues.put(KEY_ROW_STATUS,status);


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
