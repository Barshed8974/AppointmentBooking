package com.perennialsys.appointmentbooking.entityClasses;

import javax.persistence.*;

@Entity
public class Patient {
    @GeneratedValue
    @Id
    private long patientId;
    private String patientName;
    private String toDiagnose;
    private String uID="pat";
    private String password;

    public Patient() {
    }

    public Patient(long patientId, String patientName, String toDiagnose, String password) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.toDiagnose = toDiagnose;
        this.password=password;
        uID=patientName+patientId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getToDiagnose() {
        return toDiagnose;
    }

    public void setToDiagnose(String toDiagnose) {
        this.toDiagnose = toDiagnose;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}