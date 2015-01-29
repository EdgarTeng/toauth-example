package com.tenchael.toauth2.provider.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.tenchael.toauth2.provider.domian.StatusMsg;

public interface StatusMsgService extends BaseService<StatusMsg, Long> {

	List<StatusMsg> findAllVisible();

	List<StatusMsg> findAllVisible(Long userId);

	Page<StatusMsg> findAllVisible(Pageable pageable);

	Page<StatusMsg> findAllVisible(Long userId, Pageable pageable);

	List<StatusMsg> findAllVisible(Sort sort);

	List<StatusMsg> findAllVisible(Long userId, Sort sort);

}
