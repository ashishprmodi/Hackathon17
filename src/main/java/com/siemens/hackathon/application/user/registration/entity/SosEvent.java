package com.siemens.hackathon.application.user.registration.entity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
@ToString
public class SosEvent {

	@NotNull
	@JsonProperty("emergencyContactNumber1")
	private String emergencyContactNumber1;

	@JsonProperty("emergencyContactNumber2")
	private String emergencyContactNumber2;

	@JsonProperty("emergencyContactNumber3")
	private String emergencyContactNumber3;

	@NotNull
	@JsonProperty("lat")
	private String latitude;
	@NotNull
	@JsonProperty("long")
	private String longitude;

}
