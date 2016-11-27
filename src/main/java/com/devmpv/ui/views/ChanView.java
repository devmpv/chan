package com.devmpv.ui.views;

import com.devmpv.ui.forms.MessageEditor;
import com.devmpv.ui.forms.PopupViewer;

public interface ChanView {
	MessageEditor getEditor();

	PopupViewer getPopup();
}
