package com.ro.staticdata.domain.repo;

import java.util.List;

import com.ro.staticdata.domain.model.Country;

public interface ICountryRepo {
	
	List<Country> findAll();
	
	Country findByName(String name);
	
	List<Country> findByRegion(String region);

}
