package com.devmpv.ui.forms;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.easyuploads.UploadField.FieldType;

import com.devmpv.model.Message;
import com.devmpv.service.MessageService;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Message editor form
 * 
 * @author devmpv
 */
@SpringComponent
@UIScope
public class MessageEditor extends Window {

	public interface ChangeHandler {

		void onChange();
	}

	private static final long serialVersionUID = -7818474729853684893L;

	private Message message;

	private final TextField title = new TextField("Title");
	private final TextArea text = new TextArea("Text");

	public TextArea getText() {
		return text;
	}

	private final Button save = new Button("Save", FontAwesome.SAVE);
	private final Button cancel = new Button("Cancel", FontAwesome.UNDO);
	private final UploadField upload = new UploadField();
	private Image image1 = new Image();

	HorizontalLayout actions = new HorizontalLayout(save, cancel);

	@Autowired
	public MessageEditor(MessageService msgSvc) {
		VerticalLayout mainLayout = new VerticalLayout(title, text, upload, image1, actions);
		mainLayout.setSpacing(false);
		mainLayout.setMargin(true);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(text, 1);
		setModal(false);
		setContent(mainLayout);
		setResizable(true);
		setClosable(false);
		setVisible(false);
		setWidth("30%");
		setHeight("45%");
		title.setWidth("100%");
		text.setSizeFull();
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
		upload.setCaption(null);
		upload.setFieldType(FieldType.FILE);
		upload.setAcceptFilter("image/*");
		upload.setMaxFileSize(2097152);
		upload.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 4616130244428056797L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				File file = (File) event.getProperty().getValue();
				if (null == file) {
					Image tmp = new Image();
					mainLayout.replaceComponent(image1, tmp);
					image1 = tmp;
					image1.setVisible(false);
					return;
				}
				image1.setHeight(100, Unit.PIXELS);
				image1.setWidth(100, Unit.PIXELS);
				image1.setSource(new FileResource(file));
				image1.setVisible(true);
			}
		});

		save.addClickListener(listener -> {
			try {
				msgSvc.saveMessage(message, upload.getValue());
				upload.clear();
			} catch (Exception e) {
				Notification.show("Error", e.getLocalizedMessage(), Notification.Type.HUMANIZED_MESSAGE);
			}
		});
		cancel.addClickListener(e -> {
			setVisible(false);
			upload.clear();
		});
	}

	public final void editMessage() {
		if (null == message) {
			message = new Message("", "");
		}
		BeanFieldGroup.bindFieldsUnbuffered(message, this);
		setVisible(true);
	}

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> {
			if (null != message.getId()) {
				h.onChange();
				message = new Message("", "");
			}
		});
	}
}
