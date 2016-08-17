package com.devmpv.ui;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.devmpv.model.ChanMessage;
import com.devmpv.model.MessageRepository;
import com.devmpv.ui.forms.MessageEditor;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

	private static final long serialVersionUID = -1978928524166597899L;

	private Grid grid;
	private Button addNewBtn;
	private TextField filter = new TextField();
	private MessageEditor editor;

	@Autowired
	private MessageRepository repo;

	@Autowired
	public VaadinUI(MessageRepository repo, MessageEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid();
		this.filter = new TextField();
		this.addNewBtn = new Button("New message", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		setContent(mainLayout);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		grid.setHeightByRows(10);
		grid.setColumns("title", "text");
		filter.setInputPrompt("Filter by last name");
		filter.addTextChangeListener(e -> listMessages(e.getText()));
		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editMessage(new ChanMessage("", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listMessages(filter.getValue());
		});
		listMessages("");
	}

	// tag::listMessages[]
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void listMessages(String text) {
		if (StringUtils.isEmpty(text)) {
			grid.setContainerDataSource(new BeanItemContainer(ChanMessage.class, (Collection) repo.findAll()));
		} else {
			grid.setContainerDataSource(
					new BeanItemContainer(ChanMessage.class, (Collection) repo.findByTitleStartsWithIgnoreCase(text)));
		}
	}
	// end::listMessages[]
}