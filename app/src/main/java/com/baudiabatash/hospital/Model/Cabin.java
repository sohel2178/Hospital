package com.baudiabatash.hospital.Model;

/**
 * Created by Sohel on 1/7/2017.
 */

public class Cabin {

    private int id;
    private String name;
    private int allocated_patient_id;
    private int status;

    public Cabin(int id, String name, int allocated_patient_id, int status) {
        this.id = id;
        this.name = name;
        this.allocated_patient_id = allocated_patient_id;
        this.status = status;
    }

    public Cabin(String name, int allocated_patient_id, int status) {
        this.name = name;
        this.allocated_patient_id = allocated_patient_id;
        this.status = status;
    }

    public Cabin(String name) {
        this.name = name;
        this.allocated_patient_id=0;
        this.status=0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAllocated_patient_id() {
        return allocated_patient_id;
    }

    public void setAllocated_patient_id(int allocated_patient_id) {
        this.allocated_patient_id = allocated_patient_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
