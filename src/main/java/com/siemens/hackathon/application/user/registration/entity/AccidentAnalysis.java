package com.siemens.hackathon.application.user.registration.entity;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AccidentAnalysis {

	Set<String> area;
	List<Map<String,List<Integer>>> countList;
}
