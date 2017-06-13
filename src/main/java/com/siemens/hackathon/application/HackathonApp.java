package com.siemens.hackathon.application;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siemens.hackathon.application.user.registration.entity.ActionItem;
import com.siemens.hackathon.application.user.registration.entity.Alarm;
import com.siemens.hackathon.application.user.registration.entity.AlarmHistory;
import com.siemens.hackathon.application.user.registration.entity.User;

@SpringBootApplication
@EnableJpaRepositories
public class HackathonApp extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(HackathonApp.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HackathonApp.class);
    }

	@Bean
	CommandLineRunner runner() {
		return args -> {
			User user = new User();
			user.setContactNumber("917387460106");
			user.setBloodGrp("B+");
			user.setEmergencyContact1("918007530200");
			user.setEmergencyContact2("917387460102");
			user.setEmergencyContact3("917387460112");
			user.setId(1l);
			user.setName("Kanika Kapoor");
			user.setVehicleType("2W");
			user.setInsuranceNo("102128129128121028");
			Alarm alarm = new Alarm();
			alarm.setId(1l);
			alarm.setLatitude("18.53100857");
			alarm.setLongitude("73.84438992");
			alarm.setVehicleType("2W");
			alarm.setUserID(1l);
			Map<String, List<Integer>> countMap = new HashMap <String, List<Integer>>();
			Integer[] i = {121, 99, 222, 250, 102, 160, 110, 120};
			countMap.put("tw",Arrays.asList(i));
			Integer[] i1 = {12, 9, 22, 20, 12, 16, 11, 12};
			countMap.put("fw",Arrays.asList(i1));
			Integer[] i2 = {100, 30, 112, 20, 212, 116, 115, 212};
			countMap.put("hv",Arrays.asList(i2));
			AlarmHistory almHistory =  new AlarmHistory(countMap, new GregorianCalendar(2017,Calendar.JANUARY,31).getTime());
			ActionItem item = new ActionItem("Police",false, "URGENT", "");
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.writeValueAsString(user));
			System.out.println(mapper.writeValueAsString(alarm));
			System.out.println(mapper.writeValueAsString(almHistory));
			System.out.println(mapper.writeValueAsString(item));
		};
	}
}
