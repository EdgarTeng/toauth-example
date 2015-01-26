package com.tenchael.toauth2.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.toauth2.provider.domian.User;
import com.tenchael.toauth2.provider.domian.UserDetails;
import com.tenchael.toauth2.provider.respository.UserDao;
import com.tenchael.toauth2.provider.service.UserDetailsService;
import com.tenchael.toauth2.provider.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordHelper passwordHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	public User get(Long id) {
		return userDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public User save(User entity) {
		entity.setCreateTime(new java.util.Date());
		// 加密密码
		passwordHelper.encryptPassword(entity);
		User user = userDao.save(entity);
		UserDetails userDetails = new UserDetails(user, new java.util.Date());
		userDetailsService.save(userDetails);
		return user;
	}

	@Transactional(readOnly = false)
	public User update(User entity) {
		User queryUser = get(entity.getId());
		entity.setCreateTime(queryUser.getCreateTime());
		// 加密密码
		passwordHelper.encryptPassword(entity);
		return userDao.save(entity);
	}

	@Transactional(readOnly = false)
	public User delete(Long id) {
		User user = get(id);
		userDetailsService.delete(user.getUserDetails().getId());
		userDao.delete(id);
		return user;
	}

	public List<User> findAll() {
		return userDao.findAll();
	}

	public Page<User> findAll(Pageable pageable) {
		return userDao.findAll(pageable);
	}

	public User getByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Transactional(readOnly = false)
	public User modifyPassword(Long userId, String newPassword) {
		User user = userDao.findOne(userId);
		user.setPassword(newPassword);
		passwordHelper.encryptPassword(user);
		userDao.save(user);
		return user;

	}

}
