package com.tenchael.toauth2.provider.service.impl;

import java.util.List;
import java.util.UUID;

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

import com.tenchael.toauth2.provider.domian.Client;
import com.tenchael.toauth2.provider.respository.ClientDao;
import com.tenchael.toauth2.provider.service.ClientService;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientDao clientDao;

	public Client findOne(Long id) {
		return clientDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public Client save(Client entity) {
		entity.setCreateTime(new java.util.Date());
		entity.setClientId(UUID.randomUUID().toString());
		entity.setClientSecret(UUID.randomUUID().toString());
		return clientDao.save(entity);
	}

	@Transactional(readOnly = false)
	public Client update(Client entity) {
		Client queryClient = findOne(entity.getId());
		entity.setCreateTime(queryClient.getCreateTime());
		return clientDao.save(entity);
	}

	@Transactional(readOnly = false)
	public Client delete(Long id) {
		Client client = findOne(id);
		clientDao.delete(id);
		return client;
	}

	public List<Client> findAll() {
		return clientDao.findAll();
	}

	public Page<Client> findAll(Pageable pageable) {
		return clientDao.findAll(pageable);
	}

	public Client findByClientId(final String clientId) {
		Specification<Client> spec = new Specification<Client>() {

			public Predicate toPredicate(Root<Client> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<String> get("clientId"), clientId);
			}
		};
		List<Client> list = clientDao.findAll(spec);
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public Client findByClientSecret(final String clientSecret) {
		Specification<Client> spec = new Specification<Client>() {

			public Predicate toPredicate(Root<Client> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb
						.equal(root.<String> get("clientSecret"), clientSecret);
			}
		};
		List<Client> list = clientDao.findAll(spec);
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
