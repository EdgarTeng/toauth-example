package com.tenchael.toauth2.provider.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenchael.toauth2.provider.domian.User;

public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
