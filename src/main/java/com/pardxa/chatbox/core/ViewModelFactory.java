package com.pardxa.chatbox.core;

import com.pardxa.chatbox.utils.Constants;
import com.pardxa.chatbox.view.UIViewModel;

public class ViewModelFactory {
	private ModelFactory modelFactory;
	private UIViewModel uiViewModel;

	public ViewModelFactory(ModelFactory modelFactory) {
		this.modelFactory = modelFactory;
	}

	public UIViewModel createUIViewModel() {
		if (uiViewModel == null) {
			uiViewModel = new UIViewModel(modelFactory.createUserListServiceInstance(Constants.USER_LIST_CLASS_NAME),
					modelFactory.createMessageExchangeService(Constants.MESSAGE_EXCHANGE_CLASS_NAME),
					modelFactory.createMessageCacheService());
		}
		return uiViewModel;
	}
}
