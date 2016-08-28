package com.devmpv.ui;

import java.time.Instant;

import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MPanel;

import com.devmpv.model.Message;

public class MessageLayout extends MPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7811192741949505972L;

	public MessageLayout(Message message) {
		String caption = String.format("№%08d", message.getId()).concat(" ").concat(message.getTitle()).concat(" ")
				.concat(Instant.ofEpochMilli(message.getTimestamp()).toString());
		RichText text = new RichText(message.getText());
		setCaption(caption);
		setContent(text);
	}

}
