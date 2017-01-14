package com.baudiabatash.hospital.Model;

import java.io.Serializable;

/**
 * Created by Sohel on 1/3/2017.
 */

public class Patient implements Serializable{

    private int id;
    private String patient_id;
    private String admission_date;
    private String patient_name;
    private String guardian_name;
    private String contact;
    private String address;
    private int ref_doctor_id;
    private int allocated_cabin_id;


    public Patient() {
    }

    public Patient(String patient_id, String admission_date, String patient_name, String guardian_name, String contact,String address, int ref_doctor_id, int allocated_cabin_id) {
        this.patient_id = patient_id;
        this.admission_date = admission_date;
        this.patient_name = patient_name;
        this.guardian_name = guardian_name;
        this.contact = contact;
        this.address = address;
        this.ref_doctor_id = ref_doctor_id;
        this.allocated_cabin_id = allocated_cabin_id;
    }

    public Patient(int id, String patient_id, String admission_date, String patient_name, String guardian_name, String contact,String address, int ref_doctor_id, int allocated_cabin_id) {
        this.id = id;
        this.patient_id = patient_id;
        this.admission_date = admission_date;
        this.patient_name = patient_name;
        this.guardian_name = guardian_name;
        this.contact = contact;
        this.address = address;
        this.ref_doctor_id = ref_doctor_id;
        this.allocated_cabin_id = allocated_cabin_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getRef_doctor_id() {
        return ref_doctor_id;
    }

    public void setRef_doctor_id(int ref_doctor_id) {
        this.ref_doctor_id = ref_doctor_id;
    }

    public int getAllocated_cabin_id() {
        return allocated_cabin_id;
    }

    public void setAllocated_cabin_id(int allocated_cabin_id) {
        this.allocated_cabin_id = allocated_cabin_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getAdmission_date() {
        return admission_date;
    }

    public void setAdmission_date(String admission_date) {
        this.admission_date = admission_date;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getGuardian_name() {
        return guardian_name;
    }

    public void setGuardian_name(String guardian_name) {
        this.guardian_name = guardian_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
