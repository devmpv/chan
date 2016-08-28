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

	private Button addNewBtn;
	private TextField filter = new TextField();
	private MessageEditor editor;

	private LazyList lazylist;

	@Autowired
	public ThreadUI(MessageRepository repo, MessageEditor editor, MessageService msgSvc) {
		this.editor = editor;
		this.filter = new TextField();
		this.lazylist = new LazyList(() -> {
			return msgSvc.getMoreMessages(10);
		});
		this.addNewBtn = new Button("New message", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		addWindow(editor);
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, lazylist);
		setContent(mainLayout);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		filter.setInputPrompt("Filter title");
		// filter.addTextChangeListener(e -> listMessages(e.getText()));
		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> {
			editor.setVisible(true);
			editor.editMessage(new Message("", ""));
		});

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			// listMessages(filter.getValue());
		});
		// listMessages("");
	}

	// tag::listMessages[]
	/*
	 * @SuppressWarnings({ "rawtypes", "unchecked" }) private void
	 * listMessages(String text) { if (StringUtils.isEmpty(text)) {
	 * grid.setContainerDataSource(new BeanItemContainer(ChanMessage.class,
	 * (Collection) repo.findAll())); } else { grid.setContainerDataSource( new
	 * BeanItemContainer(ChanMessage.class,
	 * repo.findByTitleStartsWithIgnoreCase(text))); } }
	 */
	// end::listMessages[]
}