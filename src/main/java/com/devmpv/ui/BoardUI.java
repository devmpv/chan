package com.devmpv.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.devmpv.service.MessageService;
import com.devmpv.ui.forms.MessageEditor;
import com.devmpv.ui.forms.PopupViewer;
import com.devmpv.ui.views.ThreadView;
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

	private Navigator navigator;
	private ThreadView threadView;
	private MessageService msgSvc;

	@Autowired
	public BoardUI(MessageService msgSvc) {
		this.msgSvc = msgSvc;
	}

	public MessageEditor getEditor() {
		return threadView.getEditor();
	}

	public PopupViewer getPopup() {
		return threadView.getPopup();
	}

	@Override
	protected void init(VaadinRequest request) {
		threadView = new ThreadView(msgSvc);
		navigator = new Navigator(this, this);
		navigator.addView("thread", threadView);
		navigator.navigateTo("thread");
	}
}
