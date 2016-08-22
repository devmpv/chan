package com.devmpv.ui.forms;

import org.springframework.beans.factory.annotation.Autowired;

import com.devmpv.model.ChanMessage;
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

	/**
	 * The currently edited message
	 */
	private ChanMessage message;

	TextField title = new TextField("Title");
	RichTextArea text = new RichTextArea("Text");
	/* Action buttons */
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);

	CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public MessageEditor(MessageRepository repository) {
		this.repository = repository;
		title.setWidth("100%");
		VerticalLayout mainLayout = new VerticalLayout(title, text, actions);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		setModal(true);
		setContent(mainLayout);
		setResizable(false);
		center();
		setVisible(false);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> {
			repository.save(message);
			this.setVisible(false);
		});
		delete.addClickListener(e -> repository.delete(message));
		cancel.addClickListener(e -> editMessage(message));
	}

	public final void editMessage(ChanMessage msg) {
		final boolean persisted = msg.getId() != null;
		if (persisted) {
			message = repository.findOne((Long) msg.getId());
		} else {
			message = msg;
		}
		cancel.setVisible(persisted);
		BeanFieldGroup.bindFieldsUnbuffered(message, this);
		setVisible(true);
	}

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
