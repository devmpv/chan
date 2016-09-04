package com.devmpv.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.LazyList;

import com.devmpv.model.Message;
import com.devmpv.model.MessageRepository;
import com.devmpv.service.MessageService;
import com.devmpv.ui.forms.MessageEditor;
import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
public class ThreadUI extends UI {

	private static final long serialVersionUID = -1978928524166597899L;

	private static final int SIZE = 20;
	private Button addNewBtn;
	private TextField filter;
	private MessageEditor editor;
	private MessageService msgSvc;
	private LazyList lazylist;
	private int page = 0;

	@Autowired
	public ThreadUI(MessageRepository repo, MessageEditor editor, MessageService msgSvc) {
		this.editor = editor;
		this.msgSvc = msgSvc;
		this.filter = new TextField();
		this.lazylist = newList();
		this.addNewBtn = new Button("New message", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		addWindow(editor);
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, lazylist);
		setContent(mainLayout);

		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(lazylist, 1);

		filter.setInputPrompt("Filter title");
		addNewBtn.addClickListener(e -> {
			editor.setVisible(true);
			editor.editMessage(new Message("", ""));
		});

		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			mainLayout.replaceComponent(mainLayout.getComponent(1), newList());
		});
	}

	public LazyList newList() {
		page = 0;
		return new LazyList(() -> {
			int currentPage = page;
			page++;
			return msgSvc.getMoreMessages(currentPage, SIZE, null);
		});
	}
}