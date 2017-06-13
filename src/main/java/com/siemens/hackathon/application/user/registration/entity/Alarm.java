package com.siemens.hackathon.application.user.registration.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SOS_ALARM")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ALARMID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @Column(name = "LATITUDE")
    private String latitude;
    @Column(name = "LONGITUDE")
    private String longitude;
    @Column(name = "VEHICLETYPE")
    private String vehicleType;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "ALARM_TASKS_MAP", joinColumns = { @JoinColumn(name = "ALARMID") }, inverseJoinColumns = {
                    @JoinColumn(name = "ALARMTASKID") })
    private List<ActionItem> tasks;
    @Column(name = "STROKE")
    private float stroke;

    @Column(name="ALARM_CREATEDDATE")
    private Date alarmCreatedDate;
    @Column(name="OCCURENCEAREA")
    private String areaOfOccurence;
    private long uId;
}
