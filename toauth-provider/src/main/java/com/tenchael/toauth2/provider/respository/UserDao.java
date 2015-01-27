package com.tenchael.toauth2.provider.respository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tenchael.toauth2.provider.domian.User;

public interface UserDao extends JpaRepository<User, Long> {

	List<User> findAll(Specification<User> spec);

}
