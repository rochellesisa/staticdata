package com.ro.staticdata.domain.repo;

import java.util.List;

import com.ro.staticdata.domain.model.ErrorLog;

public interface IErrorLogRepo {

	ErrorLog save(ErrorLog log);

	<S extends ErrorLog> List<S> saveAll(Iterable<S> entities);


}
