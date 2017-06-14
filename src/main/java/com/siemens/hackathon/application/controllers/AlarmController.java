package com.siemens.hackathon.application.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.siemens.hackathon.application.controllers.error.ResponseError;
import com.siemens.hackathon.application.helpers.ReverseGeocodingHelper;
import com.siemens.hackathon.application.repositories.ActionItemRepository;
import com.siemens.hackathon.application.repositories.AlarmRepository;
import com.siemens.hackathon.application.repositories.UserRepository;
import com.siemens.hackathon.application.user.registration.entity.AccidentAnalysis;
import com.siemens.hackathon.application.user.registration.entity.ActionItem;
import com.siemens.hackathon.application.user.registration.entity.Alarm;
import com.siemens.hackathon.application.user.registration.entity.AlarmDto;
import com.siemens.hackathon.application.user.registration.entity.AlarmHistory;
import com.siemens.hackathon.application.user.registration.entity.HeatChartReponse;
import com.siemens.hackathon.application.user.registration.entity.SosEvent;
import com.siemens.hackathon.application.user.registration.entity.User;

@RestController
@RequestMapping(value = "/alarm")
@CrossOrigin
public class AlarmController {

	@Autowired
	AlarmRepository alarmRepo;
	@Autowired
	SmsService smsService;
	@Autowired
	UserRepository userRepo;
	@Autowired
	ActionItemRepository taskRepo;
	@Autowired
	ReverseGeocodingHelper geocodeHelper;
	@PersistenceContext
	private EntityManager em;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Long> createAlarm(@RequestBody Alarm alarm) {
		ActionItem craneItem;

		alarm.setAlarmCreatedDate(new Date());
		alarm.setAreaOfOccurence(getAddressFromCoordinates(alarm.getLatitude(), alarm.getLongitude()));
		Alarm createdAlarm = alarmRepo.save(alarm);

		User user = userRepo.findOne(alarm.getUId());
		smsService.sendSms(new SosEvent(user.getEmergencyContact1(), user.getEmergencyContact2(),
				user.getEmergencyContact3(), alarm.getLatitude(), alarm.getLongitude()));

		// create tasks
		List<ActionItem> tasks = new ArrayList<ActionItem>();

		ActionItem policeItem = new ActionItem("Police", false, "URGENT", createdAlarm.getLatitude(),
				createdAlarm.getLongitude(), null);
		ActionItem hospitalItem = new ActionItem("Hospital", false, "URGENT", createdAlarm.getLatitude(),
				createdAlarm.getLongitude(), null);
		ActionItem bloodBankItem = new ActionItem("Insurance", false, "URGENT", createdAlarm.getLatitude(),
				createdAlarm.getLongitude(), null);

		if (createdAlarm.getStroke() >= 70) {
			craneItem = new ActionItem("Crane", false, "URGENT", createdAlarm.getLatitude(),
					createdAlarm.getLongitude(), null);
			tasks.add(craneItem);
		}

		tasks.add(policeItem);
		tasks.add(bloodBankItem);
		tasks.add(hospitalItem);

		// tasks.forEach(t -> taskRepo.save(t));

		createdAlarm.setTasks(tasks);
		createdAlarm.setUser(user);
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

	@GetMapping(produces = "application/json", value = "/occurencebyarea")
	public ResponseEntity<List<Map<String, String>>> getOccurenceCountByArea() {
		List<Map<String, String>> occurenceAreaCounts = new ArrayList<Map<String, String>>();
		@SuppressWarnings("unchecked")
		List<Object[]> results = em
				.createNativeQuery(
						"SELECT COUNT(*) AS TOTAL, OCCURENCEAREA AS AREA FROM SOS_ALARM GROUP BY OCCURENCEAREA")
				.getResultList();
		for (Object[] result : results) {
			String name = (String) result[1];
			int count = ((Number) result[0]).intValue();
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			map.put("count", Integer.toString(count));
			occurenceAreaCounts.add(map);
		}
		return new ResponseEntity<List<Map<String, String>>>(occurenceAreaCounts, HttpStatus.OK);
	}

	private String getAddressFromCoordinates(String latitude, String longitude) {
		return geocodeHelper.reverseGeocode(latitude, longitude);
	}

	@GetMapping(value = "/alluser")
	public List<User> fetchAllUserAlarms() throws JsonProcessingException {
		List<User> users = userRepo.findAll();
		return users;
	}

	@GetMapping(value = "/alarmData")
	public List<AlarmDto> fetchAllAlarmData() throws JsonProcessingException {
		List<Alarm> alarms = alarmRepo.findAllByOrderByAlarmCreatedDateDesc();
		List<AlarmDto> alarmDataObjects = new ArrayList<>();
		alarms.forEach(a -> {
			List<ActionItem> tasks = a.getTasks();

			for (ActionItem task : tasks) {

				AlarmDto alarmDto = new AlarmDto();
				alarmDto.setUsername(a.getUser().getName());
				alarmDto.setAlarmCreatedDate(a.getAlarmCreatedDate());
				alarmDto.setBloodGrp(a.getUser().getBloodGrp());
				alarmDto.setContactNumber(a.getUser().getContactNumber());
				alarmDto.setEmergencyContact1(a.getUser().getEmergencyContact1());
				alarmDto.setEmergencyContact2(a.getUser().getEmergencyContact2());
				alarmDto.setEmergencyContact3(a.getUser().getEmergencyContact3());
				alarmDto.setInsuranceNo(a.getUser().getInsuranceNo());
				alarmDto.setGeoLocation(a.getLatitude() + " " + a.getLongitude());
				alarmDto.setAlarmTaskId(task.getAlarmTaskId());
				alarmDto.setTaskType(task.getType());
				alarmDto.setStatus(task.getStatus());
				alarmDto.setFeedback(task.getFeedback());
				alarmDto.setAcknowledged(task.isAcknowledged());
				alarmDto.setVehicleType(a.getUser().getVehicleType());

				alarmDataObjects.add(alarmDto);
			}

		});
		return alarmDataObjects;
	}

	@GetMapping(value = "/alarmTasks")
	public List<ActionItem> fetchAllAlarmTasks() throws JsonProcessingException {
		List<Alarm> alarms = alarmRepo.findAll();
		List<ActionItem> tasks = new ArrayList<ActionItem>();

		alarms.forEach(a -> {
			List<ActionItem> alarmTasks = a.getTasks();
			for (ActionItem item : alarmTasks) {
				tasks.add(item);
			}

		});
		return tasks;

	}

	@PutMapping(value = "/updateStatus")
	public void updateTaskState(@PathVariable String status, @PathVariable Long id) {
		ActionItem task = taskRepo.findOne(id);
		task.setStatus(status);
		taskRepo.save(task);
	}
	
	@PutMapping(value = "//task/ack/{taskId}")
	public ResponseEntity<?>  acknowledgeTask(@PathVariable String taskId) {
		ActionItem task = taskRepo.findOne(Long.parseLong(taskId));
		if(null != task)
			task.setAcknowledged(!task.isAcknowledged());
		taskRepo.save(task);
		return new ResponseEntity<AccidentAnalysis>(HttpStatus.OK);
	}

	@GetMapping(produces = "application/json", value = "/getAccidentAnalysisData")
	public ResponseEntity<?> getAccidentAnalysisData() {
		List<Alarm> alarms = alarmRepo.findAll();
		if (alarms.isEmpty())
			return new ResponseEntity<ResponseError>(
					new ResponseError(HttpStatus.NOT_FOUND.value(), "no alarms found."), HttpStatus.NOT_FOUND);
		Set<String> area = new HashSet<String>();
		List<Map<String, List<Integer>>> countList = new ArrayList<Map<String, List<Integer>>>();
		alarms.forEach(a -> area.add(a.getAreaOfOccurence()));
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();

		Integer[] cList = new Integer[area.size()];
		Integer[] mList = new Integer[area.size()];
		Integer[] iList = new Integer[area.size()];
		Arrays.fill(cList, 0);
		Arrays.fill(mList, 0);
		Arrays.fill(iList, 0);
		Iterator<String> i = area.iterator();
		int cnt = 0;
		while (i.hasNext()) {
			String name = i.next();
			for (Alarm a : alarms) {
				if (a.getAreaOfOccurence().equals(name)) {
					if (a.getStroke() >= 85) {
						cList[cnt]++;
					}
					if (a.getStroke() >= 75 && a.getStroke() < 85) {
						mList[cnt]++;
					}
					if (a.getStroke() >= 70 && a.getStroke() < 75) {
						iList[cnt]++;
					}
				}
			}
			cnt++;
		}
		Map<String, List<Integer>> cMap = new HashMap<String, List<Integer>>();
		cMap.put("c", Arrays.asList(cList));
		countList.add(cMap);
		cMap = new HashMap<String, List<Integer>>();
		cMap.put("m", Arrays.asList(mList));
		countList.add(cMap);
		cMap = new HashMap<String, List<Integer>>();
		cMap.put("i", Arrays.asList(iList));
		countList.add(cMap);
		AccidentAnalysis response = new AccidentAnalysis(area, countList);
		return new ResponseEntity<AccidentAnalysis>(response, HttpStatus.OK);
	}
}
