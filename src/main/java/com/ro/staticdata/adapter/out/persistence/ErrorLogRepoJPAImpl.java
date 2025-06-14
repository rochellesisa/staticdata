package com.ro.staticdata.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import com.ro.staticdata.domain.model.ErrorLog;
import com.ro.staticdata.domain.repo.IErrorLogRepo;

@RestController
public interface ErrorLogRepoJPAImpl extends JpaRepository<ErrorLog, Integer>, IErrorLogRepo {

}
