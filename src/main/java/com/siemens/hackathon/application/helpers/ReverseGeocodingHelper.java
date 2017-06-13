package com.siemens.hackathon.application.helpers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Service
public class ReverseGeocodingHelper {

	@Autowired
	@Qualifier("rtWithProxy")
	private RestTemplate restTemplate;

	public String reverseGeocode(String latitude, String longitude) {
		String long_name = null;
		try {
			URI url = new URI("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude
					+ "&sensor=true");
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>("", headers);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			JSONObject json = (JSONObject) JSONSerializer.toJSON(response.getBody());
			JSONArray results = json.getJSONArray("results");
			JSONObject rec = results.getJSONObject(0);
			JSONArray address_components = rec.getJSONArray("address_components");
			for (int i = 0; i < address_components.size(); i++) {
				JSONObject rec1 = address_components.getJSONObject(i);
				JSONArray types = rec1.getJSONArray("types");
				if (types.size() == 3 && types.getString(2).equalsIgnoreCase("sublocality_level_1")) {
					String comp = types.getString(2);
					System.out.println("city ————-" + rec1.getString("long_name"));
					long_name = rec1.getString("long_name");
					break;
				}
			}
			// formatted_address = rec.getString("formatted_address");
			// conn.disconnect();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return long_name;
	}

}