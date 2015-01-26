package com.tenchael.toauth2.provider.service;

import com.tenchael.toauth2.provider.domian.UserDetails;

public interface UserDetailsService extends BaseService<UserDetails, Long> {

	UserDetails getByUserId(Long userId);

}
