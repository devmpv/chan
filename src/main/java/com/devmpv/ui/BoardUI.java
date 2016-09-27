package com.devmpv.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ChameleonTheme;

@SpringUI
@Theme(ChameleonTheme.THEME_NAME)
public class BoardUI extends UI {

	private static final long serialVersionUID = 4069459695649654746L;

	Navigator navigator;

	@Override
	protected void init(VaadinRequest request) {

	}

}
