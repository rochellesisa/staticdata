package com.ro.staticdata.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ro.staticdata.domain.model.Country;
import com.ro.staticdata.domain.repo.ICountryRepo;

@Repository
public interface CountryRepoJPAImpl extends JpaRepository<Country, Integer>, ICountryRepo {

	@Override
	@Query("SELECT o FROM Country o WHERE o.region.name = :name")
	List<Country> findByRegion(@Param("name") String region);
}
