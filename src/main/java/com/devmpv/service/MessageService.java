package com.devmpv.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.devmpv.model.MessageRepository;
import com.devmpv.ui.MessageLayout;
import com.vaadin.ui.Component;

@Service
public class MessageService {

	@Autowired
	private MessageRepository repo;

	@Autowired
	private AttachmentService service;

	public List<Component> getMoreMessages(int page, int size, Sort sort) {
		List<Component> result = new ArrayList<>();
		repo.findAll(new PageRequest(page, size, sort)).forEach(msg -> result.add(new MessageLayout(msg, service)));
		return result;
	}
}
