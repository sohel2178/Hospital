package com.baudiabatash.hospital.Model;

import java.io.Serializable;

/**
 * Created by Sohel on 1/12/2017.
 */

public class Test implements Serializable {

    private int id;
    private String date;
    private String test;
    private int status;
    private int patient_id;

    public Test() {
    }

    public Test(String date,String test, int status, int patient_id) {
        this.date =date;
        this.test = test;
        this.status = status;
        this.patient_id = patient_id;
    }

    public Test(int id, String date,String test, int status, int patient_id) {
        this(date,test,status,patient_id);
        this.id = id;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }
}
