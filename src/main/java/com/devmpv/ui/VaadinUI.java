package com.devmpv.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

	private static final long serialVersionUID = -1978928524166597899L;

	@Override
	protected void init(VaadinRequest request) {
		setContent(new Button("Click me", e -> Notification.show("Hello Spring+Vaadin user!")));
	}
}