package com.siemens.hackathon.application.user.registration.entity;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class HeatChartReponse {

	List<String> latLong;
}
