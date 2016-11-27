package com.devmpv.ui.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.devmpv.config.Constant;
import com.devmpv.model.Thread;
import com.devmpv.service.MessageService;
import com.devmpv.ui.forms.MessageEditor;
import com.devmpv.ui.forms.PopupViewer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = Constant.URI_THREAD)
public class ThreadView extends VerticalLayout implements View, ChanView {

	private static final long serialVersionUID = -3492322718789886012L;

	private static final int SIZE = 500;

	@Autowired
	private MessageService msgSvc;

	private Button addNewBtn;
	private TextField filter;
	private MessageEditor editor;
	private PopupViewer popup;
	private Thread thread;

	private VerticalLayout messageList;
	private int page = 0;

	public ThreadView() {
		this.filter = new TextField();
		this.addNewBtn = new Button("Reply", FontAwesome.PLUS);
		this.popup = new PopupViewer();
	}

	private void createThreadView(String parameters) {
		thread = getThread(parameters);
		if (null == thread) {
			Notification.show("Error", "No such thread: ".concat(parameters), Notification.Type.HUMANIZED_MESSAGE);
			addComponent(new VerticalLayout());
			return;
		}
		this.messageList = newList();
		this.editor = new MessageEditor(msgSvc, thread, thread.getBoard());
		UI.getCurrent().addWindow(editor);
		UI.getCurrent().addWindow(popup);

		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, messageList);
		addComponent(mainLayout);

		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(messageList, 1);

		filter.setInputPrompt("Search...");
		addNewBtn.addClickListener(e -> {
			editor.setVisible(true);
			editor.editMessage();
		});

		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			messageList = newList();
			mainLayout.replaceComponent(mainLayout.getComponent(1), messageList);
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		createThreadView(event.getParameters());
	}

	@Override
	public MessageEditor getEditor() {
		return editor;
	}

	@Override
	public PopupViewer getPopup() {
		return popup;
	}

	private Thread getThread(String parameters) {
		try {
			return msgSvc.getThreadById(Long.valueOf(parameters));
		} catch (Exception e) {
			return null;
		}
	}

	public VerticalLayout newList() {
		VerticalLayout result = new VerticalLayout();
		result.setSpacing(true);
		msgSvc.getMoreMessages(thread, page, SIZE, null).forEach(comp -> result.addComponent(comp));
		return result;
	}
}
