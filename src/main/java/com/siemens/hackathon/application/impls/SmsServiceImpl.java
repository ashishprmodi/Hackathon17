package com.siemens.hackathon.application.impls;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.siemens.hackathon.application.controllers.SmsService;
import com.siemens.hackathon.application.user.registration.entity.SosEvent;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    @Qualifier("rtWithoutProxy")
    private RestTemplate restTemplate;

    @Value("${sms.service.sender}")
    private String sender;
    @Value("${sms.service.delivery.message}")
    private String message;
    @Value("${sms.service.api.username}")
    private String apiUser;
    @Value("${sms.service.api.pwd}")
    private String apiPwd;
    @Value("${sms.service.api.get.uri}")
    private String apiUrl;
    @Value("${sms.service.map.uri}")
    private String mapUri;

    private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Override
    public ResponseEntity<?> sendSms(SosEvent event) {
        ResponseEntity<String> response = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>("", headers);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("User", apiUser));
            nameValuePairs.add(new BasicNameValuePair("passwd", apiPwd));

            String emgContact = event.getEmergencyContactNumber1();
            String emgContact2 = event.getEmergencyContactNumber2();
            if(null != emgContact2)
                emgContact = emgContact.concat(","+emgContact2);
            String emgContact3 = event.getEmergencyContactNumber3();
            if(null != emgContact3)
                emgContact = emgContact.concat(","+emgContact3);

            nameValuePairs.add(new BasicNameValuePair("mobilenumber", emgContact));
            nameValuePairs.add(new BasicNameValuePair("message", message + mapUri + event.getLatitude() + "," + event.getLongitude() + " "));
            nameValuePairs.add(new BasicNameValuePair("sid", sender));
            nameValuePairs.add(new BasicNameValuePair("mtype", "N"));
            nameValuePairs.add(new BasicNameValuePair("DR", "Y"));
            URIBuilder builder = new URIBuilder().setScheme("http").setHost(apiUrl).setParameters(nameValuePairs);
            response = restTemplate.exchange(builder.build(), HttpMethod.GET, entity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        log.info(response.toString());
        return response;
    }

}
