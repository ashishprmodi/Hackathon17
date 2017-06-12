package com.siemens.hackathon.application.user.registration.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
	private long id;
	private Long userID;
	@Column(name = "LATITUDE")
	private String latitude;
	@Column(name = "LONGITUDE")
	private String longitude;
	@Column(name = "VEHICLETYPE")
	private String vehicleType;
}
