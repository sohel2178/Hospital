package com.baudiabatash.hospital.Model;

/**
 * Created by Sohel on 1/12/2017.
 */

public class Notes {
    private int id;
    private String date;
    private String notes;
    private int patient_id;

    public Notes() {
    }

    public Notes(String date, String notes, int patient_id) {
        this.date = date;
        this.notes = notes;
        this.patient_id = patient_id;
    }

    public Notes(int id, String date, String notes, int patient_id) {
        this.id = id;
        this.date = date;
        this.notes = notes;
        this.patient_id = patient_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}
