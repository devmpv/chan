package com.devmpv.ui.forms;

import java.io.File;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.easyuploads.UploadField.FieldType;

import com.devmpv.model.Attachment;
import com.devmpv.model.Message;
import com.devmpv.model.MessageRepository;
import com.devmpv.service.AttachmentService;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

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

	private final MessageRepository msgRepo;
	private final AttachmentService attachSvc;

	private Message message;

	private final TextField title = new TextField("Title");
	private final RichTextArea text = new RichTextArea("Text");
	private final Button save = new Button("Save", FontAwesome.SAVE);
	private final Button cancel = new Button("Cancel", FontAwesome.EJECT);
	private final UploadField upload = new UploadField();
	private Image image1 = new Image();

	CssLayout actions = new CssLayout(save, cancel);

	@Autowired
	public MessageEditor(MessageRepository repository, AttachmentService attachSvc) {
		this.attachSvc = attachSvc;
		this.msgRepo = repository;
		VerticalLayout mainLayout = new VerticalLayout(title, text, upload, image1, actions);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		setModal(false);
		setContent(mainLayout);
		setResizable(false);
		setClosable(false);
		setVisible(false);
		title.setWidth("100%");
		text.setWidth("100%");
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		cancel.setStyleName(ValoTheme.BUTTON_QUIET);
		cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
		upload.setFieldType(FieldType.FILE);
		upload.setAcceptFilter("image/*");
		upload.setMaxFileSize(512000);
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

		save.addClickListener(e -> {
			message.setTimestamp(System.currentTimeMillis());
			Attachment attachment;
			if (!upload.isEmpty()) {
				attachment = this.attachSvc.add((File) upload.getValue());
				if (null != attachment.getId()) {
					HashSet<Attachment> value = new HashSet<>();
					value.add(attachment);
					message.setAttachments(value);
				}
			}
			repository.save(message);
			this.setVisible(false);
		});
		cancel.addClickListener(e -> this.setVisible(false));
	}

	public final void editMessage(Message msg) {
		final boolean persisted = msg.getId() != null;
		if (persisted) {
			message = msgRepo.findOne(msg.getId());
		} else {
			message = msg;
		}
		BeanFieldGroup.bindFieldsUnbuffered(message, this);
		setVisible(true);
	}

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
	}
}
