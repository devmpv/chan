package com.devmpv.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ChanMessageRepository {
	@Autowired
	HibernateOperations operations;

	public ChanMessage get(long id) {
		return operations.get(ChanMessage.class, id);
	}

	@Transactional(readOnly = false)
	public void save(ChanMessage msg) {
		operations.saveOrUpdate(msg);
	}
}
