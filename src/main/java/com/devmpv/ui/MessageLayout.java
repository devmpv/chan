package com.devmpv.ui;

import java.time.Instant;

import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MPanel;

import com.devmpv.model.Message;
import com.devmpv.service.AttachmentService;
import com.vaadin.server.FileResource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;

public class MessageLayout extends MPanel {

	private static final long serialVersionUID = 7811192741949505972L;

	public MessageLayout(Message message, AttachmentService service) {
		String caption = String.format("№%08d", message.getId()).concat(" ").concat(message.getTitle()).concat(" ")
				.concat(Instant.ofEpochMilli(message.getTimestamp()).toString());
		RichText text = new RichText(message.getText());
		setCaption(caption);
		HorizontalLayout msgLayout = new HorizontalLayout();
		msgLayout.setMargin(true);
		msgLayout.setSpacing(true);
		service.getFileSet(message.getAttachments()).forEach(file -> {
			Image img = new Image("", new FileResource(file));
			img.setHeight(100, Unit.PIXELS);
			img.setWidth(100, Unit.PIXELS);
			msgLayout.addComponent(img);
		});
		msgLayout.addComponent(text);
		setContent(msgLayout);
	}

}
