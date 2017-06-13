package com.siemens.hackathon.application.user.registration.entity;

import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access=AccessLevel.PUBLIC)
@Data
public class OccurenceAreaResponse {

	List<Map<String, String>> occurenceAreaCounts;
}
