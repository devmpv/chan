package com.devmpv.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.devmpv.model.MessageRepository;
import com.devmpv.service.MessageService;
import com.devmpv.ui.forms.MessageEditor;
import com.vaadin.annotations.Theme;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ChameleonTheme;

@SpringUI
@Theme(ChameleonTheme.THEME_NAME)
public class ThreadUI extends UI {

	private static final long serialVersionUID = -1978928524166597899L;

	private static final int SIZE = 500;
	private Button addNewBtn;
	private TextField filter;
	private MessageEditor editor;
	private MessageService msgSvc;
	private VerticalLayout messageList;
	private Window popup;
	private int page = 0;

	@Autowired
	public ThreadUI(MessageRepository repo, MessageEditor editor, MessageService msgSvc) {
		this.editor = editor;
		this.msgSvc = msgSvc;
		this.filter = new TextField();
		this.messageList = newList();
		this.addNewBtn = new Button("Reply", FontAwesome.PLUS);
		this.popup = new Window();
	}

	@Override
	protected void init(VaadinRequest request) {
		addWindow(editor);
		addWindow(popup);
		popup.setVisible(false);
		popup.setModal(false);
		popup.center();
		popup.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 2307250604465836302L;

			@Override
			public void click(ClickEvent event) {
				popup.setVisible(false);
			}
		});
		msgSvc.setPopup(popup);
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, messageList);
		setContent(mainLayout);

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

	public VerticalLayout newList() {
		VerticalLayout result = new VerticalLayout();
		result.setSpacing(true);
		msgSvc.getMoreMessages(page, SIZE, null).forEach(comp -> result.addComponent(comp));
		return result;
	}
}