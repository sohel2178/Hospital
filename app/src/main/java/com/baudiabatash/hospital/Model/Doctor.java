package com.baudiabatash.hospital.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Sohel on 1/6/2017.
 */

public class Doctor implements Serializable {

    private int id;
    private String name;
    private String designation;
    private String degree;
    private String organization;

    public Doctor() {
    }


    public Doctor(String name, String designation, String degree, String organization) {
        this.name = name;
        this.designation = designation;
        this.degree = degree;
        this.organization = organization;
    }

    public Doctor(int id,String name, String designation, String degree, String organization) {
        this.id =id;
        this.name = name;
        this.designation = designation;
        this.degree = degree;
        this.organization = organization;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDoctorObject(){
        JSONObject doctor = new JSONObject();
        try{

            doctor.put("id",id);
            doctor.put("name",name);
            doctor.put("designation",designation);
            doctor.put("degree",degree);
            doctor.put("organization",organization);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return doctor.toString();

    }
}
