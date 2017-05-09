package com.chanzor.office.modules.sys.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFactory {
	public static <T> ResponseEntity<T> createResponseEntity(T o) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("restStatus", "200");
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<T>(o, headers, HttpStatus.OK);
	}

	public static ResponseEntity<String> createFailResponseEntity(String o) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("restStatus", "500");
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<String>(o, headers, HttpStatus.OK);
	}
}
