package com.tenchael.toauth2.provider.respository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tenchael.toauth2.provider.domian.StatusMsg;

public interface StatusMsgDao extends JpaRepository<StatusMsg, Long> {

	Page<StatusMsg> findAll(Specification<StatusMsg> spec, Pageable pageable);

	List<StatusMsg> findAll(Specification<StatusMsg> spec);

}
