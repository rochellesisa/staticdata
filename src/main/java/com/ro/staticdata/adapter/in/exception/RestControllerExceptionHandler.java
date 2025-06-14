package com.ro.staticdata.adapter.in.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ro.staticdata.domain.dto.RestResponse;

@RestControllerAdvice
@SuppressWarnings("rawtypes")
public class RestControllerExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<RestResponse> handleError(Exception e){
		RestResponse response = new RestResponse<>(false, e.getMessage(), null);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
