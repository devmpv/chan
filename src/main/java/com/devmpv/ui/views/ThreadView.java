package com.devmpv.ui.views;

import com.devmpv.model.Thread;
import com.devmpv.service.MessageService;
import com.devmpv.ui.forms.MessageEditor;
import com.devmpv.ui.forms.PopupViewer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ThreadView extends VerticalLayout implements View {

	private static final long serialVersionUID = -3492322718789886012L;

	private static final int SIZE = 500;

	private Button addNewBtn;
	private TextField filter;
	private MessageEditor editor;
	private PopupViewer popup;
	private Thread thread;
	private MessageService msgSvc;

	private VerticalLayout messageList;
	private int page = 0;

	public ThreadView(MessageService msgSvc) {
		Thread thread = msgSvc.getThreadById(0);
		if (null == thread) {
			thread = msgSvc.saveThread(new Thread());
		}
		this.thread = thread;
		this.msgSvc = msgSvc;
		this.filter = new TextField();
		this.messageList = newList();
		this.addNewBtn = new Button("Reply", FontAwesome.PLUS);
		this.popup = new PopupViewer();
		this.editor = new MessageEditor(msgSvc, thread);
	}

	private void createThreadView() {
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
		createThreadView();
	}

	public MessageEditor getEditor() {
		return editor;
	}

	public PopupViewer getPopup() {
		return popup;
	}

	public VerticalLayout newList() {
		VerticalLayout result = new VerticalLayout();
		result.setSpacing(true);
		msgSvc.getMoreMessages(page, SIZE, null).forEach(comp -> result.addComponent(comp));
		return result;
	}
}
