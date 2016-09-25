package com.devmpv.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.devmpv.model.Attachment;
import com.devmpv.model.Message;
import com.devmpv.model.MessageRepository;
import com.devmpv.ui.MessageLayout;
import com.devmpv.ui.forms.MessageEditor;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Window;

@Service
public class MessageService {

	@Autowired
	private MessageRepository msgRepo;

	@Autowired
	private AttachmentService attachSvc;

	private MessageEditor editor;

	private ClickListener replyListener = new ClickListener() {
		private static final long serialVersionUID = -4154995187910480613L;

		@Override
		public void buttonClick(ClickEvent event) {
			editor.editMessage();
			editor.getText().setValue(
					editor.getText().getValue().concat(">").concat((String) event.getButton().getData()).concat("\n"));
		}
	};

	private com.vaadin.event.MouseEvents.ClickListener popupListener = new com.vaadin.event.MouseEvents.ClickListener() {
		private static final long serialVersionUID = 6236981282218000159L;

		@Override
		public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
			Attachment attach = (Attachment) ((Image) event.getComponent()).getData();
			if (!attach.equals(popup.getData())) {
				Image img = new Image(null, new FileResource(attachSvc.getFile(attach)));
				popup.setContent(img);
				popup.center();
				popup.setData(((Image) event.getComponent()).getData());
				popup.setVisible(true);
			} else {
				popup.setVisible(!popup.isVisible());
			}
		}
	};

	private TextProcessor processor = BBProcessorFactory.getInstance().create();

	private Window popup;

	public Message findOne(Long id) {
		return msgRepo.findOne(id);
	}

	public MessageEditor getEditor() {
		return editor;
	}

	public List<Component> getMoreMessages(int page, int size, Sort sort) {
		List<Component> result = new ArrayList<>();
		msgRepo.findAll(new PageRequest(page, size, sort))
				.forEach(msg -> result.add(new MessageLayout(msg, attachSvc, this)));
		return result;
	}

	public com.vaadin.event.MouseEvents.ClickListener getPopupListener() {
		return popupListener;
	}

	public ClickListener getReplyListener() {
		return replyListener;
	}

	@Transactional
	public Message saveMessage(Message message, Object object) throws Exception {
		message.setTimestamp(System.currentTimeMillis());
		Attachment attachment;
		if (null != object) {
			attachment = attachSvc.add((File) object);
			message.getAttachments().add(attachment);
		}
		message.setText(processor.process(message.getText()));
		return msgRepo.save(message);
	}

	public void setEditor(MessageEditor editor) {
		this.editor = editor;
	}

	public void setPopup(Window popup) {
		this.popup = popup;
	}
}
