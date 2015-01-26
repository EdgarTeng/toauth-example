package com.tenchael.toauth2.provider.respository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tenchael.toauth2.provider.domian.UserDetails;

public interface UserDetailsDao extends JpaRepository<UserDetails, Long> {

	List<UserDetails> findAll(Specification<UserDetails> spec);

}
