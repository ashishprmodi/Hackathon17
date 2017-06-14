package com.siemens.hackathon.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.siemens.hackathon.application.user.registration.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

	List<Alarm> findByVehicleType(String vehicleType);
	List<Alarm> findAllByOrderByAlarmCreatedDateDesc();
	
}