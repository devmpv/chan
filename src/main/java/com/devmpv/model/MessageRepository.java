package com.devmpv.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<ChanMessage, Serializable> {
	List<ChanMessage> findByTitleStartsWithIgnoreCase(String title);
}
