package com.devmpv.ui.views;

import com.devmpv.model.BoardEnum;
import com.devmpv.service.MessageService;
import com.devmpv.ui.forms.MessageEditor;
import com.devmpv.ui.forms.PopupViewer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class BoardView extends VerticalLayout implements View {

	private static final long serialVersionUID = 2631303138958190462L;

	private static final int SIZE = 500;

	private Button addNewBtn;
	private TextField filter;
	private MessageEditor editor;
	private PopupViewer popup;
	private BoardEnum board;
	private MessageService msgSvc;

	private VerticalLayout threadList;
	private int page = 0;

	public BoardView(MessageService msgSvc) {
		this.msgSvc = msgSvc;
		this.filter = new TextField();
		this.addNewBtn = new Button("New thread", FontAwesome.PLUS);
		this.popup = new PopupViewer();
	}

	private void createBoardView(String parameters) {
		board = BoardEnum.valueOf(parameters);
		if (null == board) {
			Notification.show("Error", "No such board: ".concat(parameters), Notification.Type.HUMANIZED_MESSAGE);
			addComponent(new VerticalLayout());
			return;
		}
		this.threadList = newList();
		this.editor = new MessageEditor(msgSvc, null, board);
		UI.getCurrent().addWindow(editor);
		UI.getCurrent().addWindow(popup);

		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, threadList);
		addComponent(mainLayout);

		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(threadList, 1);

		filter.setInputPrompt("Search...");

		addNewBtn.addClickListener(e -> {
			editor.setVisible(true);
			editor.editMessage();
		});
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			threadList = newList();
			mainLayout.replaceComponent(mainLayout.getComponent(1), threadList);
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		createBoardView(event.getParameters());
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
		msgSvc.getMoreThreads(board, page, SIZE, null).forEach(comp -> result.addComponent(comp));
		return result;
	}
}
