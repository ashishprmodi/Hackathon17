package com.siemens.hackathon.application.user.registration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@Table(name="SOS_ALARMTASK")
public class ActionItem {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ALARMTASKID")
	@Getter
	@Setter
	private long alarmTaskId;
	
	@Setter
	@Getter
	@Column(name="TYPE")
	private String type;
/*	@Column(name="ALARMID")
	private long alarmId;*/
	
	@Setter
	@Getter
	@Column(name="ACKNOWLEDGED")
	private boolean acknowledged;
	
	@Setter
	@Getter
	@Column(name="STATUS")
	private String status;
	
	@Setter
	@Getter
	@Column(name="FEEDBACK")
	private String feedback;
	
	public ActionItem(String type, boolean ack, String status, String feedbk){
		this.type = type;
		this.acknowledged = ack;
		this.status=status;
		this.feedback=feedbk;
		
	}
	
	
}
