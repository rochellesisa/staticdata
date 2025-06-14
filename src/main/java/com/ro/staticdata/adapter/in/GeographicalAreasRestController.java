package com.ro.staticdata.adapter.in;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ro.staticdata.application.port.in.IGeographicalAreaFacade;
import com.ro.staticdata.domain.dto.GenericResult;
import com.ro.staticdata.domain.dto.GeoDataDTO;
import com.ro.staticdata.domain.dto.RestResponse;

@RestController
@RequestMapping("/api/v1.0/geo")
public class GeographicalAreasRestController {

	private IGeographicalAreaFacade facade;
	
	public GeographicalAreasRestController(IGeographicalAreaFacade facade) {
		this.facade = facade;
	}

	@GetMapping("/countries")
	public ResponseEntity<RestResponse<List<GeoDataDTO>>> getAllCountries() {
		RestResponse<List<GeoDataDTO>> response = new RestResponse<>(true, null,
				facade.getAllCountries());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/region/{region}")
	public ResponseEntity<RestResponse<List<GeoDataDTO>>> getAllCountriesByRegion(@PathVariable String region) {
		RestResponse<List<GeoDataDTO>> response = new RestResponse<>(true, null,
				facade.getAllCountriesByRegion(region));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/countries/{country}/cities")
	public ResponseEntity<RestResponse<List<GeoDataDTO>>> getAllCitiesByCountry(@PathVariable String country) {
		RestResponse<List<GeoDataDTO>> response = new RestResponse<>(true, null,
				facade.getAllCitiesByCountry(country));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/countries/{country}")
	public ResponseEntity<RestResponse<GeoDataDTO>> getCountry(@PathVariable String country) {
		RestResponse<GeoDataDTO> response = new RestResponse<>(true, null,
				facade.getCountryWithCities(country));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/refresh")
	public ResponseEntity<RestResponse<GeoDataDTO>> refreshData() {
		GenericResult result = facade.refreshData();
		RestResponse<GeoDataDTO> response = new RestResponse<>(result.isSuccess(), result.getMessage(), null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
