package com.tenchael.toauth2.provider.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T, ID> {

	T findOne(ID id);

	T save(T entity);

	T update(T entity);

	T delete(ID id);

	List<T> findAll();

	Page<T> findAll(Pageable pageable);

}
