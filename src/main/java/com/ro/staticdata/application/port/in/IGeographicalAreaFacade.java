package com.ro.staticdata.application.port.in;

import java.util.List;

import com.ro.staticdata.domain.dto.GenericResult;
import com.ro.staticdata.domain.dto.GeoDataDTO;

public interface IGeographicalAreaFacade {
		
	List<GeoDataDTO> getAllCountries();
	
	List<GeoDataDTO> getAllCountriesByRegion(String regionName);
	
	List<GeoDataDTO> getAllCitiesByCountry(String countryName);
	
	GeoDataDTO getCountryWithCities(String countryName);
	
	GenericResult refreshData();
	
}
