package com.tenchael.toauth2.provider.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.toauth2.provider.domian.User;
import com.tenchael.toauth2.provider.domian.UserDetails;
import com.tenchael.toauth2.provider.respository.UserDetailsDao;
import com.tenchael.toauth2.provider.service.UserDetailsService;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserDetailsServiceImpl.class);
	@Autowired
	private UserDetailsDao userDetailsDao;

	public UserDetails findOne(Long id) {
		return userDetailsDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public UserDetails save(UserDetails entity) {
		return userDetailsDao.save(entity);
	}

	@Transactional(readOnly = false)
	public UserDetails update(UserDetails entity) {
		return userDetailsDao.save(entity);
	}

	@Transactional(readOnly = false)
	public UserDetails delete(Long id) {
		logger.info("delete UserDetails id={}", id);
		UserDetails userDetails = findOne(id);
		userDetailsDao.delete(id);
		return userDetails;
	}

	public List<UserDetails> findAll() {
		return userDetailsDao.findAll();
	}

	public Page<UserDetails> findAll(Pageable pageable) {
		return userDetailsDao.findAll(pageable);
	}

	public UserDetails getByUserId(final Long userId) {
		Specification<UserDetails> spec = new Specification<UserDetails>() {

			public Predicate toPredicate(Root<UserDetails> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<User> get("user").<Long> get("id"),
						userId);
			}

		};
		List<UserDetails> list = userDetailsDao.findAll(spec);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

}
