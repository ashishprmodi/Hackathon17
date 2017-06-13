package com.siemens.hackathon.application.user.registration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SOS_USER")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class User {

    private long id;

    private String name;

    private String bloodGrp;

    private String contactNumber;

    private String emergencyContact1;

    private String emergencyContact2;

    private String emergencyContact3;

    private String vehicleType;

    private String insuranceNo;

    //private List<Alarm> alarms;

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "BGRP")
    public String getBloodGrp() {
        return bloodGrp;
    }

    public void setBloodGrp(String bloodGrp) {
        this.bloodGrp = bloodGrp;
    }

    @Column(name = "CONTACTNUMBER")
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Column(name = "EMERGENCYCONTACT1")
    public String getEmergencyContact1() {
        return emergencyContact1;
    }

    public void setEmergencyContact1(String emergencyContact1) {
        this.emergencyContact1 = emergencyContact1;
    }

    @Column(name = "EMERGENCYCONTACT2")
    public String getEmergencyContact2() {
        return emergencyContact2;
    }

    public void setEmergencyContact2(String emergencyContact2) {
        this.emergencyContact2 = emergencyContact2;
    }

    @Column(name = "EMERGENCYCONTACT3")
    public String getEmergencyContact3() {
        return emergencyContact3;
    }

    public void setEmergencyContact3(String emergencyContact3) {
        this.emergencyContact3 = emergencyContact3;
    }

    @Column(name = "VEHICLETYPE")
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Column(name = "INSURANCENO")
    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    /* @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ALARMS", joinColumns = {@JoinColumn(name = "USERID")}, inverseJoinColumns = {@JoinColumn(name = "ALARMID")})
    public List<Alarm> getAlarms() {
        return alarms;
    }*/

    /* public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USERID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
