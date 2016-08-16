package com.devmpv.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.devmpv.model.ChanMessage;
import com.devmpv.model.ChanMessageRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

	private static final long serialVersionUID = -1978928524166597899L;

	private Grid grid = new Grid();
	private TextField filter = new TextField();

	@Autowired
	private ChanMessageRepository repo;

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid);
		setContent(mainLayout);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		grid.setHeightByRows(10);
		grid.setColumns("id", "title", "message");
		filter.setInputPrompt("Filter by last name");
		listCustomers("");
	}

	// tag::listCustomers[]
	@SuppressWarnings("unchecked")
	private void listCustomers(String text) {
		if (StringUtils.isEmpty(text)) {
			grid.setContainerDataSource(new BeanItemContainer<>(ChanMessage.class, repo.getAll()));
		} else {
			grid.setContainerDataSource(new BeanItemContainer<>(ChanMessage.class, repo.findByText(text)));
		}
	}
	// end::listCustomers[]
}