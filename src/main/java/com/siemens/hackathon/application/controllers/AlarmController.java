package com.siemens.hackathon.application.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siemens.hackathon.application.controllers.error.ResponseError;
import com.siemens.hackathon.application.repositories.ActionItemRepository;
import com.siemens.hackathon.application.repositories.AlarmRepository;
import com.siemens.hackathon.application.repositories.UserRepository;
import com.siemens.hackathon.application.user.registration.entity.ActionItem;
import com.siemens.hackathon.application.user.registration.entity.Alarm;
import com.siemens.hackathon.application.user.registration.entity.AlarmHistory;
import com.siemens.hackathon.application.user.registration.entity.HeatChartReponse;
import com.siemens.hackathon.application.user.registration.entity.SosEvent;
import com.siemens.hackathon.application.user.registration.entity.User;

@RestController
@RequestMapping(value = "/alarm")
public class AlarmController {

	@Autowired
	AlarmRepository alarmRepo;
	@Autowired
	SmsService smsService;
	@Autowired
	UserRepository userRepo;
	@Autowired
	ActionItemRepository taskRepo;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Long> createAlarm(@RequestBody Alarm alarm) {
		ActionItem craneItem;
		
		alarm.setAlarmCreatedDate(new Date());
		Alarm createdAlarm = alarmRepo.save(alarm);
		
		User user = userRepo.findOne(alarm.getUserID());
		smsService.sendSms(new SosEvent(user.getEmergencyContact1(), user.getEmergencyContact2(),
				user.getEmergencyContact3(), alarm.getLatitude(), alarm.getLongitude()));
		
		// create tasks
		Set<ActionItem> tasks = new HashSet<ActionItem>();
		
		
		ActionItem policeItem = new ActionItem("Police",false, "URGENT", null);
		ActionItem hospitalItem = new ActionItem("Hospital", false, "URGENT", null);
		ActionItem bloodBankItem = new ActionItem("Insurance", false, "URGENT", null);
		
		if(createdAlarm.getStroke() >= 70){
			craneItem = new ActionItem("Crane", false, "URGENT", null);
			tasks.add(craneItem);
		}
		
		tasks.add(policeItem); tasks.add(bloodBankItem); tasks.add(hospitalItem); 
		
        //tasks.forEach(t -> taskRepo.save(t));
		
		createdAlarm.setTasks(tasks);
		alarmRepo.save(createdAlarm);
		
		
		
		
		
		return new ResponseEntity<Long>(createdAlarm.getId(), HttpStatus.CREATED);

	}

	@GetMapping(produces = "application/json", value = "/{vehicleType}")
	public ResponseEntity<?> getAlarmsByVehicleType(@PathVariable String vehicleType) {
		List<Alarm> alarms = alarmRepo.findByVehicleType(vehicleType);
		if (alarms.isEmpty())
			return new ResponseEntity<ResponseError>(
					new ResponseError(HttpStatus.NOT_FOUND.value(), "no alarms found by vehicle type " + vehicleType),
					HttpStatus.NOT_FOUND);
		return new ResponseEntity<List<Alarm>>(alarms, HttpStatus.OK);
	}

	@GetMapping(produces = "application/json", value = "/heat")
	public ResponseEntity<?> getAllAlarmsForHeatChart() {
		List<Alarm> alarms = alarmRepo.findAll();
		if (alarms.isEmpty())
			return new ResponseEntity<ResponseError>(
					new ResponseError(HttpStatus.NOT_FOUND.value(), "no alarms found."), HttpStatus.NOT_FOUND);
		List<String> latLongs = new ArrayList<String>();
		alarms.forEach(a -> latLongs.add(a.getLatitude().concat(",").concat(a.getLongitude())));
		HeatChartReponse response = new HeatChartReponse(latLongs);
		return new ResponseEntity<HeatChartReponse>(response, HttpStatus.OK);
	}

	@GetMapping(produces = "application/json", value = "/area")
	public ResponseEntity<?> getVehicleTypeDataByStartDate() {
		Map<String, List<Integer>> countMap = new HashMap<String, List<Integer>>();
		Integer[] i = { 121, 99, 222, 250, 102, 160, 110, 120 };
		countMap.put("tw", Arrays.asList(i));
		Integer[] i1 = { 12, 9, 22, 20, 12, 16, 11, 12 };
		countMap.put("fw", Arrays.asList(i1));
		Integer[] i2 = { 100, 30, 112, 20, 212, 116, 115, 212 };
		countMap.put("hv", Arrays.asList(i2));
		AlarmHistory e = new AlarmHistory(countMap, new GregorianCalendar(2017, Calendar.JANUARY, 31).getTime());
		return new ResponseEntity<AlarmHistory>(e, HttpStatus.OK);
	}

}
