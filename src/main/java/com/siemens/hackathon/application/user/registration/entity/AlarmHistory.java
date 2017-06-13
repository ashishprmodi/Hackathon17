package com.siemens.hackathon.application.user.registration.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AlarmHistory {
	private Map<String, List<Integer>> countMap;
	private Date startDate;
}
