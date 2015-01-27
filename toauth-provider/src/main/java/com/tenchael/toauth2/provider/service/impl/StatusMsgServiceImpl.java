package com.tenchael.toauth2.provider.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.toauth2.provider.domian.StatusMsg;
import com.tenchael.toauth2.provider.domian.User;
import com.tenchael.toauth2.provider.respository.StatusMsgDao;
import com.tenchael.toauth2.provider.service.StatusMsgService;

@Service
@Transactional(readOnly = true)
public class StatusMsgServiceImpl implements StatusMsgService {

	@Autowired
	private StatusMsgDao statusMsgDao;

	public StatusMsg findOne(Long id) {
		return statusMsgDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public StatusMsg save(StatusMsg entity) {
		entity.setCreateTime(new java.util.Date());
		return statusMsgDao.save(entity);
	}

	@Transactional(readOnly = false)
	public StatusMsg update(StatusMsg entity) {
		return statusMsgDao.save(entity);
	}

	@Transactional(readOnly = false)
	public StatusMsg delete(Long id) {
		StatusMsg msg = findOne(id);
		statusMsgDao.delete(id);
		return msg;
	}

	public List<StatusMsg> findAll() {
		return statusMsgDao.findAll();
	}

	public Page<StatusMsg> findAll(Pageable pageable) {
		return statusMsgDao.findAll(pageable);
	}

	public List<StatusMsg> findAllVisible(final Long userId) {
		Specification<StatusMsg> spec = new Specification<StatusMsg>() {

			public Predicate toPredicate(Root<StatusMsg> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.equal(root.<User> get("user")
						.<Long> get("id"), userId));
				predicates.add(cb.equal(root.<Boolean> get("visible"), true));
				cb.and(predicates.toArray(new Predicate[predicates.size()]));
				return cb.conjunction();
			}
		};
		return statusMsgDao.findAll(spec);
	}

	public Page<StatusMsg> findAllVisible(final Long userId, Pageable pageable) {
		Specification<StatusMsg> spec = new Specification<StatusMsg>() {

			public Predicate toPredicate(Root<StatusMsg> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.equal(root.<User> get("user")
						.<Long> get("id"), userId));
				predicates.add(cb.equal(root.<Boolean> get("visible"), true));
				cb.and(predicates.toArray(new Predicate[predicates.size()]));
				return cb.conjunction();
			}
		};
		return statusMsgDao.findAll(spec, pageable);
	}

	public List<StatusMsg> findAllVisible() {
		Specification<StatusMsg> spec = new Specification<StatusMsg>() {

			public Predicate toPredicate(Root<StatusMsg> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<Boolean> get("visible"), true);
			}
		};
		return statusMsgDao.findAll(spec);
	}

	public Page<StatusMsg> findAllVisible(Pageable pageable) {
		Specification<StatusMsg> spec = new Specification<StatusMsg>() {

			public Predicate toPredicate(Root<StatusMsg> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<Boolean> get("visible"), true);
			}
		};
		return statusMsgDao.findAll(spec, pageable);
	}

}
