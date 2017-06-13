package com.siemens.hackathon.application.user.registration.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlarmDto {

    private String username;

    private String bloodGrp;

    private String contactNumber;

    private String emergencyContact1;

    private String emergencyContact2;

    private String emergencyContact3;

    private String vehicleType;

    private String insuranceNo;

    private long alarmTaskId;

    private String taskType;

    private boolean acknowledged;

    private String status;

    private String feedback;

    @JsonProperty("GeoLocation")
    private String geoLocation;

    private Date alarmCreatedDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBloodGrp() {
        return bloodGrp;
    }

    public void setBloodGrp(String bloodGrp) {
        this.bloodGrp = bloodGrp;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmergencyContact1() {
        return emergencyContact1;
    }

    public void setEmergencyContact1(String emergencyContact1) {
        this.emergencyContact1 = emergencyContact1;
    }

    public String getEmergencyContact2() {
        return emergencyContact2;
    }

    public void setEmergencyContact2(String emergencyContact2) {
        this.emergencyContact2 = emergencyContact2;
    }

    public String getEmergencyContact3() {
        return emergencyContact3;
    }

    public void setEmergencyContact3(String emergencyContact3) {
        this.emergencyContact3 = emergencyContact3;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public long getAlarmTaskId() {
        return alarmTaskId;
    }

    public void setAlarmTaskId(long alarmTaskId) {
        this.alarmTaskId = alarmTaskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public Date getAlarmCreatedDate() {
        return alarmCreatedDate;
    }

    public void setAlarmCreatedDate(Date alarmCreatedDate) {
        this.alarmCreatedDate = alarmCreatedDate;
    }

}
