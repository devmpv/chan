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
import com.devmpv.model.BoardEnum;
import com.devmpv.model.Message;
import com.devmpv.model.MessageRepository;
import com.devmpv.model.Thread;
import com.devmpv.model.ThreadRepository;
import com.devmpv.ui.forms.PopupViewer;
import com.devmpv.ui.layouts.MessageLayout;
import com.devmpv.ui.layouts.ThreadPreviewLayout;
import com.devmpv.ui.views.ChanView;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;

@Service
public class MessageService {

	@Autowired
	private MessageRepository msgRepo;

	@Autowired
	private AttachmentService attachSvc;

	@Autowired
	private ThreadRepository threadRepo;

	private ClickListener popupListener = new ClickListener() {
		private static final long serialVersionUID = 6236981282218000159L;

		@Override
		public void click(ClickEvent event) {
			PopupViewer popup = ((ChanView) UI.getCurrent().getNavigator().getCurrentView()).getPopup();
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

	public List<Message> getMessages(Thread thread) {
		return msgRepo.findByThreadOrderByTimestamp(thread);
	}

	public List<Component> getMoreMessages(Thread thread, int page, int size, Sort sort) {
		List<Component> result = new ArrayList<>();
		msgRepo.findByThread(thread, new PageRequest(page, size, sort))
				.forEach(msg -> result.add(new MessageLayout(msg, attachSvc, this)));
		return result;
	}

	public List<Component> getMoreThreads(BoardEnum board, int page, int size, Sort sort) {
		List<Component> result = new ArrayList<>();
		threadRepo.findByBoard(board, new PageRequest(page, size, sort))
				.forEach(thread -> result.add(new ThreadPreviewLayout(thread, attachSvc, this)));
		return result;
	}

	public com.vaadin.event.MouseEvents.ClickListener getPopupListener() {
		return popupListener;
	}

	public Thread getThreadById(long id) {
		return threadRepo.findOne(id);
	}

	@Transactional
	public Message saveMessage(Message message, Object object) throws Exception {
		threadRepo.save(message.getThread());
		Attachment attachment;
		if (null != object) {
			attachment = attachSvc.add((File) object);
			message.getAttachments().add(attachment);
		}
		message.setText(processor.process(message.getText()));
		message.setTimestamp(System.currentTimeMillis());
		return msgRepo.save(message);
	}

	@Transactional
	public Thread saveThread(Thread thread) {
		return threadRepo.save(thread);
	}
}
