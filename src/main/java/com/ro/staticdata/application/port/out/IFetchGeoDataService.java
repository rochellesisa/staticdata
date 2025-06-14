package com.ro.staticdata.application.port.out;

import java.util.List;

import com.ro.staticdata.domain.dto.GeoDataDTO;

public interface IFetchGeoDataService {
	
	List<GeoDataDTO> getAllCountries();
	
	List<GeoDataDTO> getAllCities();

}
