package com.ro.staticdata.domain.repo;

import java.util.List;

import com.ro.staticdata.domain.model.Region;

public interface IRegionRepo {

	void deleteAll();
	
	<S extends Region> List<S> saveAll(Iterable<S> entities);
	
}
