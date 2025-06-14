package com.ro.staticdata.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "geo_city")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City extends JPAEntity {

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;
	
	
}
