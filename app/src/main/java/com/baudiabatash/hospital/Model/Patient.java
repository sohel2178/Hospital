package com.baudiabatash.hospital.Model;

/**
 * Created by Sohel on 1/3/2017.
 */

public class Patient {

    private String patient_id;
    private String admission_date;
    private String patient_name;
    private String guardian_name;
    private String address;
    private String ref_doctor;
    private String allotted_cabin;


    public Patient() {
    }

    public Patient(String patient_id, String admission_date, String patient_name, String guardian_name, String address, String ref_doctor, String allotted_cabin) {
        this.patient_id = patient_id;
        this.admission_date = admission_date;
        this.patient_name = patient_name;
        this.guardian_name = guardian_name;
        this.address = address;
        this.ref_doctor = ref_doctor;
        this.allotted_cabin = allotted_cabin;
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

    public String getRef_doctor() {
        return ref_doctor;
    }

    public void setRef_doctor(String ref_doctor) {
        this.ref_doctor = ref_doctor;
    }

    public String getAllotted_cabin() {
        return allotted_cabin;
    }

    public void setAllotted_cabin(String allotted_cabin) {
        this.allotted_cabin = allotted_cabin;
    }
}