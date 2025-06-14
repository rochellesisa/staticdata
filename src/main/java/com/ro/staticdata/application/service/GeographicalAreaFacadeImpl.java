package com.ro.staticdata.application.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import com.ro.staticdata.application.port.in.IGeographicalAreaFacade;
import com.ro.staticdata.application.port.out.IFetchGeoDataService;
import com.ro.staticdata.domain.dto.GenericResult;
import com.ro.staticdata.domain.dto.GeoDataDTO;
import com.ro.staticdata.domain.model.City;
import com.ro.staticdata.domain.model.Country;
import com.ro.staticdata.domain.model.ErrorLog;
import com.ro.staticdata.domain.model.Region;
import com.ro.staticdata.domain.repo.ICountryRepo;
import com.ro.staticdata.domain.repo.IErrorLogRepo;
import com.ro.staticdata.domain.repo.IRegionRepo;

import jakarta.transaction.Transactional;

@Service
public class GeographicalAreaFacadeImpl implements IGeographicalAreaFacade {
	
	private static final Logger LOGGER = LogManager.getLogger(GeographicalAreaFacadeImpl.class);

	private IRegionRepo regionRepo;
	private ICountryRepo countryRepo;
	private IErrorLogRepo errorLogRepo;
	private IFetchGeoDataService fetchGeoService;

	public GeographicalAreaFacadeImpl(IRegionRepo regionRepo, ICountryRepo countryRepo, IErrorLogRepo errorLogRepo,
			IFetchGeoDataService fetchGeoService) {
		super();
		this.regionRepo = regionRepo;
		this.countryRepo = countryRepo;
		this.errorLogRepo = errorLogRepo;
		this.fetchGeoService = fetchGeoService;
	}

	@Override
	public List<GeoDataDTO> getAllCountries() {
		LOGGER.info("Getting all countries");
		return countryRepo.findAll().stream().map(c -> new GeoDataDTO(c)).collect(Collectors.toList());
	}

	@Override
	public List<GeoDataDTO> getAllCountriesByRegion(String regionName) {
		LOGGER.info(String.format("Getting all countries from [%s]", regionName));
		return countryRepo.findByRegion(regionName).stream().map(c -> new GeoDataDTO(c)).collect(Collectors.toList());
	}

	@Override
	public List<GeoDataDTO> getAllCitiesByCountry(String countryName) {
		LOGGER.info(String.format("Getting all cities of [%s]", countryName));
		return countryRepo.findAll().stream().map(c -> {
			return new GeoDataDTO(c, c.getCities());
		}).collect(Collectors.toList());
	}

	@Override
	public GeoDataDTO getCountryWithCities(String countryName) {
		LOGGER.info(String.format("Getting [%s] details with cities", countryName));
		Country country = countryRepo.findByName(countryName);
		List<City> cities = country.getCities();
		return new GeoDataDTO(country, cities);
	}

	@Override
	@Transactional
	public GenericResult refreshData() {
		long start = System.currentTimeMillis();
		LOGGER.info("Refreshing geographical data");
		try {
			List<GeoDataDTO> allCountries = fetchGeoService.getAllCountries();
			List<GeoDataDTO> allCities = fetchGeoService.getAllCities();
			Map<String, List<GeoDataDTO>> citiesByCountry = allCities.stream().collect(Collectors.groupingBy(GeoDataDTO::parent));
			Map<String, List<GeoDataDTO>> countriesByRegion = allCountries.stream().collect(Collectors.groupingBy(GeoDataDTO::parent));
			
			List<Region> regions = initRegions(citiesByCountry, countriesByRegion);
			List<ErrorLog> errorLogs = createErrorLogsForUncreatedCities(allCountries, citiesByCountry);
			
			LOGGER.info(Strings.repeat("=", 15));
			LOGGER.info("# of regions: " + regions.size());
			LOGGER.info("# of countries: " + allCountries.size());
			LOGGER.info("# of cities: " + allCities.size());
			LOGGER.info(Strings.repeat("=", 15));
			
			regionRepo.deleteAll();
			regionRepo.saveAll(regions);
			errorLogRepo.saveAll(errorLogs);
		} catch (Exception e) {
			LOGGER.error("Error encountered while refreshing geographical data", e);
			ErrorLog log = new ErrorLog("GEOGRAPHICAL", "Error encountered: " + e.getMessage(), null);
			errorLogRepo.save(log);
			return new GenericResult(false, e.getMessage());
		}
		
		LOGGER.info("Refresh ended in " + (System.currentTimeMillis() - start) + "ms");
		return new GenericResult(true);

	}

	private List<ErrorLog> createErrorLogsForUncreatedCities(List<GeoDataDTO> allCountries,
			Map<String, List<GeoDataDTO>> citiesByCountry) {
		Set<String> countryName = allCountries.stream().map(GeoDataDTO::name).distinct().collect(Collectors.toSet());
		Set<String> missingCountries = citiesByCountry.keySet().stream().filter(c -> !countryName.contains(c)).collect(Collectors.toSet());
		List<ErrorLog> errorLogs = missingCountries.stream().flatMap(country -> citiesByCountry.get(country).stream()).map(city -> {
			String message = String.format("Unable to create city [%s] due to unknown country [%s]", city.name(), city.parent());
			LOGGER.info(message);
			
			ErrorLog log = new ErrorLog();
			log.setEntity("CITY");
			log.setMessage(message);
			log.setData(city.toString());
			return log;
		}).collect(Collectors.toList());
		return errorLogs;
	}

	private List<Region> initRegions(Map<String, List<GeoDataDTO>> citiesByCountry,
			Map<String, List<GeoDataDTO>> countriesByRegion) {
		List<Region> regions = countriesByRegion.entrySet().stream().map(e -> {
			String regionName = e.getKey();
			
			LOGGER.info("Setting up region:" + regionName);
			
			Region region = new Region(regionName);
			List<Country> countries = countriesByRegion.get(regionName).stream().map(c -> {
				LOGGER.info("Setting up country: " + c.name());
				Country country = new Country(c.name(), c.capital(), region);
				LOGGER.info("Setting up cities");
				List<City> cities = citiesByCountry.containsKey(c.name()) ? citiesByCountry.get(c.name()).stream().map(city -> new City(city.name(), country)).collect(Collectors.toList()) : Collections.emptyList();				
				country.setCities(cities);
				return country;
			}).collect(Collectors.toList());
			region.setCountries(countries);
			return region;
		}).collect(Collectors.toList());
		return regions;
	}

}
