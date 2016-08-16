package com.devmpv.model;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ChanMessageRepository {
	@Autowired
	HibernateOperations operations;

	public Collection findByText(String text) {
		return operations.findByCriteria(DetachedCriteria.forClass(ChanMessage.class), 0, 10);
	}

	public ChanMessage get(long id) {
		return operations.get(ChanMessage.class, id);
	}

	public List<ChanMessage> getAll() {
		return operations.loadAll(ChanMessage.class);
	}

	@Transactional(readOnly = false)
	public void save(ChanMessage msg) {
		operations.saveOrUpdate(msg);
	}
}
