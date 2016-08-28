package com.devmpv.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Serializable> {
	List<Message> findByTitleStartsWithIgnoreCase(String title);
}
