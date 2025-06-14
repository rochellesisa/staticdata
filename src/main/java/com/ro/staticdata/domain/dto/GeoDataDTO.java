package com.ro.staticdata.domain.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ro.staticdata.domain.model.City;
import com.ro.staticdata.domain.model.Country;

import lombok.ToString;

@JsonInclude(Include.NON_NULL)
public record GeoDataDTO(String name, String capital, String parent, List<GeoDataDTO> subAreas) {

	public GeoDataDTO(Country c) {
		this(c.getName(), c.getCapital(), c.getRegion().getName(), null);
	}

	public GeoDataDTO(Country country, List<City> cities) {
		this(country.getName(), country.getCapital(), country.getRegion().getName(),
				cities.stream().map(ci -> new GeoDataDTO(ci.getName(), null, null, null)).collect(Collectors.toList()));
	}

	public GeoDataDTO(City c) {
		this(c.getName(), null, c.getCountry().getName(), null);
	}
}
