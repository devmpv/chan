package com.devmpv.ui.forms;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PopupViewer extends Window {

	private static final long serialVersionUID = -9173570375902557764L;

	Panel L = new Panel();
	Panel T = new Panel();
	Panel B = new Panel();
	Panel R = new Panel();
	Panel panel = new Panel();

	private VerticalLayout v1 = new VerticalLayout(T, panel, B);
	private HorizontalLayout h1 = new HorizontalLayout(L, v1, R);

	public PopupViewer() {
		v1.setExpandRatio(panel, 1);
		v1.setSizeFull();
		h1.setExpandRatio(v1, 1);
		h1.setSizeFull();
		L.setWidth("10%");
		L.setHeight("100%");
		R.setWidth("10%");
		R.setHeight("100%");
		T.setWidth("100%");
		T.setHeight("10%");
		B.setWidth("100%");
		B.setHeight("10%");
		super.setContent(h1);
		setVisible(false);
		setModal(false);
		setSizeFull();
		center();
		setResizable(false);
		setReadOnly(true);
		addClickListener(new ClickListener() {
			private static final long serialVersionUID = 2307250604465836302L;

			@Override
			public void click(ClickEvent event) {
				setVisible(false);
			}
		});
	}

	@Override
	public void setContent(Component content) {
		if (null != content) {
			panel.setContent(content);
		}
	}

}
