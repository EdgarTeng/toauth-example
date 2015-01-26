package com.tenchael.toauth2.provider.respository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tenchael.toauth2.provider.domian.Client;

public interface ClientDao extends JpaRepository<Client, Long> {
	List<Client> findAll(Specification<Client> spec);

}
