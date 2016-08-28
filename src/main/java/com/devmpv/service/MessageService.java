package com.devmpv.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.viritin.label.RichText;

import com.devmpv.model.ChanMessage;
import com.devmpv.model.MessageRepository;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Service
public class MessageService {

	@Autowired
	private MessageRepository repo;

	private Component createMessageView(ChanMessage message) {

		Label title = new Label(String.valueOf(message.getId()) + ": " + message.getTitle());
		title.setSizeUndefined();
		title.addStyleName("name");
		RichText text = new RichText(message.getText());

		HorizontalLayout header = new HorizontalLayout(title);
		VerticalLayout mainLayout = new VerticalLayout(header, text);
		return mainLayout;
	}

	public List<Component> getMoreMessages(int count) {
		List<Component> result = new ArrayList<>();
		repo.findAll().forEach(msg -> result.add(createMessageView(msg)));
		return result;
	}
}
