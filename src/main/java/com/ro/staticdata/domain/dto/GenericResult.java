package com.ro.staticdata.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericResult {
	
	boolean success;
	String message;
	
	public GenericResult(boolean success) {
		super();
		this.success = success;
	}

}
