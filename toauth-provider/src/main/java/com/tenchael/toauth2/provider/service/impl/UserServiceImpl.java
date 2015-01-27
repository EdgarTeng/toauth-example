package com.tenchael.toauth2.provider.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

	public User findOne(Long id) {
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
		entity.getUserDetails().setUser(entity);
		entity.getUserDetails().setLastUpdate(new java.util.Date());
		User queryUser = findOne(entity.getId());
		entity.setCreateTime(queryUser.getCreateTime());
		// 加密密码
		passwordHelper.encryptPassword(entity);
		return userDao.save(entity);
	}

	@Transactional(readOnly = false)
	public User delete(Long id) {
		User user = findOne(id);
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

	public User findByUsername(final String username) {
		Specification<User> spec = new Specification<User>() {
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<String> get("username"), username);
			}
		};
		List<User> list = userDao.findAll(spec);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Transactional(readOnly = false)
	public User modifyPassword(Long userId, String newPassword) {
		User user = userDao.findOne(userId);
		user.setPassword(newPassword);
		passwordHelper.encryptPassword(user);
		userDao.save(user);
		return user;

	}

	public User getCurrentUser() {
		Subject subject = SecurityUtils.getSubject();
		if (subject == null || !subject.isAuthenticated()) {
			return null;
		}

		String username = (String) subject.getPrincipal();
		return findByUsername(username);
	}

}
