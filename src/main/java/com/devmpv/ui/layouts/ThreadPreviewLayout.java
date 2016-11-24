package com.devmpv.ui.layouts;

import com.devmpv.model.Thread;
import com.devmpv.service.AttachmentService;
import com.devmpv.service.MessageService;
import com.vaadin.ui.Panel;

public class ThreadPreviewLayout extends Panel {

	private static final long serialVersionUID = 7811192741949505972L;

	/*
	 * private static final ClickListener replyListener = new ClickListener() {
	 * private static final long serialVersionUID = -4154995187910480613L;
	 * 
	 * @Override public void buttonClick(ClickEvent event) { MessageEditor
	 * editor = ((BoardUI) UI.getCurrent()).getEditor(); editor.editMessage();
	 * editor.getText().setValue(
	 * editor.getText().getValue().concat(">").concat((String)
	 * event.getButton().getData()).concat("\n")); } };
	 */

	public ThreadPreviewLayout(Thread thread, AttachmentService service, MessageService msgSvc) {
		setContent(new MessageLayout(msgSvc.getMessages(thread).stream().findFirst().get(), service, msgSvc));
		/*
		 * VerticalLayout mainLayout = new VerticalLayout(); HorizontalLayout
		 * msgLayout = new HorizontalLayout(); RichText text = new
		 * RichText(message.getText()); msgLayout.setSpacing(true);
		 * msgLayout.setMargin(true);
		 * service.getFileSet(message.getAttachments()).entrySet().forEach(entry
		 * -> { Image img = new Image(null, new FileResource(entry.getValue()));
		 * img.setData(entry.getKey()); img.setHeight(100, Unit.PIXELS);
		 * img.setWidth(100, Unit.PIXELS);
		 * img.addClickListener(msgSvc.getPopupListener());
		 * msgLayout.addComponent(img); }); msgLayout.addComponent(text);
		 * mainLayout.addComponent(createHeader(message, msgSvc));
		 * mainLayout.addComponent(msgLayout); setStyleName(PANEL_BUBBLE);
		 * setContent(mainLayout); setWidthUndefined();
		 */
	}
}
