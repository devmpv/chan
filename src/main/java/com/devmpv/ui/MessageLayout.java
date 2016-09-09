package com.devmpv.ui;

import static com.vaadin.ui.themes.ChameleonTheme.PANEL_BUBBLE;

import java.time.Instant;

import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.label.RichText;

import com.devmpv.model.Message;
import com.devmpv.service.AttachmentService;
import com.devmpv.service.MessageService;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ChameleonTheme;

public class MessageLayout extends Panel {

	private static final long serialVersionUID = 7811192741949505972L;

	public MessageLayout(Message message, AttachmentService service, MessageService msgSvc) {
		VerticalLayout mainLayout = new VerticalLayout();
		HorizontalLayout msgLayout = new HorizontalLayout();
		RichText text = new RichText(message.getText());
		msgLayout.setSpacing(true);
		msgLayout.setMargin(true);
		service.getFileSet(message.getAttachments()).forEach(file -> {
			Image img = new Image(null, new FileResource(file));
			img.setHeight(100, Unit.PIXELS);
			img.setWidth(100, Unit.PIXELS);
			img.addClickListener(msgSvc.getPopupListener());
			msgLayout.addComponent(img);
		});
		msgLayout.addComponent(text);
		mainLayout.addComponent(createHeader(message, msgSvc));
		mainLayout.addComponent(msgLayout);
		setStyleName(PANEL_BUBBLE);
		setContent(mainLayout);
		setWidthUndefined();
	}

	private HorizontalLayout createHeader(Message message, MessageService msgSvc) {
		HorizontalLayout header = new HorizontalLayout();
		header.setSpacing(true);
		CheckBox checkBox = new CheckBox();
		Label title = new Label(String.format("<h4>%s<h4>", message.getTitle()), ContentMode.HTML);
		Label name = new Label("<h4>Anonymous<h4>", ContentMode.HTML);
		MLabel id = new MLabel(String.format("№%d", message.getId()));
		MLabel time = new MLabel(Instant.ofEpochMilli(message.getTimestamp()).toString());
		Button hideButton = new Button("Hide");
		hideButton.setStyleName(ChameleonTheme.BUTTON_LINK);
		Button replyButton = new Button("Reply");
		replyButton.setStyleName(ChameleonTheme.BUTTON_LINK);
		replyButton.setData(String.valueOf(message.getId()));
		replyButton.addClickListener(msgSvc.getReplyListener());
		header.addComponents(checkBox, title, name, time, id, hideButton, replyButton);
		return header;
	}
}
