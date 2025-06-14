package com.ro.staticdata.domain.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "error_log")
@Getter
@Setter
@NoArgsConstructor
public class ErrorLog extends JPAEntity {

	@Column
	private String entity;
	
	@Column
	private String message;
	
	@Column
	private String data;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime timestamp;

	public ErrorLog(String entity, String message, String data) {
		super();
		this.entity = entity;
		this.message = message;
		this.data = data;
	}
	
}
