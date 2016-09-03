package com.devmpv.ui.forms;

import org.springframework.beans.factory.annotation.Autowired;

import com.devmpv.model.Message;
import com.devmpv.model.MessageRepository;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Message editor form
 * 
 * @author devmpv
 */
@SpringComponent
@UIScope
public class MessageEditor extends Window {

	public interface ChangeHandler {

		void onChange();
	}

	private static final long serialVersionUID = -7818474729853684893L;

	private final MessageRepository repository;

	private Message message;

	TextField title = new TextField("Title");
	RichTextArea text = new RichTextArea("Text");
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel", FontAwesome.EJECT);

	CssLayout actions = new CssLayout(save, cancel);

	@Autowired
	public MessageEditor(MessageRepository repository) {
		this.repository = repository;
		title.setWidth("100%");
		text.setWidth("100%");
		VerticalLayout mainLayout = new VerticalLayout(title, text, actions);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		setModal(false);
		setContent(mainLayout);
		setResizable(true);
		center();
		setClosable(false);
		setVisible(false);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		cancel.setStyleName(ValoTheme.BUTTON_QUIET);
		cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);

		save.addClickListener(e -> {
			message.setTimestamp(System.currentTimeMillis());
			repository.save(message);
			this.setVisible(false);
		});
		cancel.addClickListener(e -> this.setVisible(false));
	}

	public final void editMessage(Message msg) {
		final boolean persisted = msg.getId() != null;
		if (persisted) {
			message = repository.findOne(msg.getId());
		} else {
			message = msg;
		}
		BeanFieldGroup.bindFieldsUnbuffered(message, this);
		setVisible(true);
	}

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
	}
}
