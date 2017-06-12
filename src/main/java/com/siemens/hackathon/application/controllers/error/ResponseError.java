package com.siemens.hackathon.application.controllers.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ResponseError {

	private int errorCode;
	private String errorMsg;

}
