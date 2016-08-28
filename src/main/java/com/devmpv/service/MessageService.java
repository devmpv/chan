package com.devmpv.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devmpv.model.MessageRepository;
import com.devmpv.ui.MessageLayout;
import com.vaadin.ui.Component;

@Service
public class MessageService {

	@Autowired
	private MessageRepository repo;

	public List<Component> getMoreMessages(int count) {
		List<Component> result = new ArrayList<>();
		repo.findAll().forEach(msg -> result.add(new MessageLayout(msg)));
		return result;
	}
}
