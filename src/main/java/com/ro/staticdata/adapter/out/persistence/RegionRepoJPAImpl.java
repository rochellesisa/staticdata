package com.ro.staticdata.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ro.staticdata.domain.model.Region;
import com.ro.staticdata.domain.repo.IRegionRepo;

public interface RegionRepoJPAImpl extends JpaRepository<Region, Integer>, IRegionRepo {

}
