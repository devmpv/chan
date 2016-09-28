package com.devmpv.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ThreadView extends VerticalLayout implements View {

	private static final long serialVersionUID = -3492322718789886012L;

	@Override
	public void enter(ViewChangeEvent event) {
		addComponent(new Label("!!!!!AAA"));
	}
}
