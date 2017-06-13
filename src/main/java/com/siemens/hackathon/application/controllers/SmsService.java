package com.siemens.hackathon.application.controllers;

import org.springframework.http.ResponseEntity;

import com.siemens.hackathon.application.user.registration.entity.SosEvent;


public interface SmsService {

    ResponseEntity<?> sendSms(SosEvent event);

}
