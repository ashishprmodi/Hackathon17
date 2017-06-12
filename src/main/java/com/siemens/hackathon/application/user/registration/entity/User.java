package com.siemens.hackathon.application.user.registration.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.FetchType;

@Entity
@Table(name = "SOS_USER")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USERID")
	private long id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "BGRP")
	private String bloodGrp;
	@Column(name = "CONTACTNUMBER")
	private String contactNumber;
	@Column(name = "EMERGENCYCONTACT1")
	private String emergencyContact1;
	@Column(name = "EMERGENCYCONTACT2")
	private String emergencyContact2;
	@Column(name = "EMERGENCYCONTACT3")
	private String emergencyContact3;
	@Column(name = "VEHICLETYPE")
	private String vehicleType;
	@Column(name = "INSURANCENO")
	private String insuranceNo;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ALARMS", joinColumns = { @JoinColumn(name = "USERID") }, inverseJoinColumns = {
			@JoinColumn(name = "ALARMID") })
	private Set<Alarm> alarms;

}
