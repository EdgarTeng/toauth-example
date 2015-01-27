package com.tenchael.toauth2.provider.service;

import com.tenchael.toauth2.provider.domian.User;

public interface UserService extends BaseService<User, Long> {

	User findByUsername(String username);

	User modifyPassword(Long userId, String newPassword);
	
	User getCurrentUser();

}
