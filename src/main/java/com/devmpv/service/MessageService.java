package com.devmpv.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.devmpv.model.Attachment;
import com.devmpv.model.Message;
import com.devmpv.model.MessageRepository;
import com.devmpv.ui.MessageLayout;
import com.vaadin.ui.Component;

@Service
public class MessageService {

	@Autowired
	private MessageRepository msgRepo;

	@Autowired
	private AttachmentService attachSvc;

	public List<Component> getMoreMessages(int page, int size, Sort sort) {
		List<Component> result = new ArrayList<>();
		msgRepo.findAll(new PageRequest(page, size, sort))
				.forEach(msg -> result.add(new MessageLayout(msg, attachSvc)));
		return result;
	}

	@Transactional
	public Message saveMessage(Message message, Object object) throws Exception {
		message.setTimestamp(System.currentTimeMillis());
		Attachment attachment;
		if (null != object) {
			attachment = attachSvc.add((File) object);
			message.getAttachments().add(attachment);
		}
		return msgRepo.save(message);
	}

	public Message findOne(Long id) {
		return msgRepo.findOne(id);
	}
}
