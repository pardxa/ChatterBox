package com.pardxa.chatbox.core;

import com.pardxa.chatbox.model.BroadcastUserListService;
import com.pardxa.chatbox.model.IMessageExchangeService;
import com.pardxa.chatbox.model.IUserListService;
import com.pardxa.chatbox.model.MessageExchangeService;

public class ModelFactory {
	private IUserListService userListService;
	private IMessageExchangeService messageExchangeService;
	private NetworkingFactory networkingFactory;

	public ModelFactory(NetworkingFactory networkingFactory) {
		this.networkingFactory = networkingFactory;
	}

	public IUserListService createUserListServiceInstance(String className) {
		if (userListService == null && className.equals(BroadcastUserListService.class.getCanonicalName())) {
			userListService = new BroadcastUserListService(networkingFactory.createUdp());
		}
		return userListService;
	}

	public IMessageExchangeService createMessageExchangeService(String className) {
		if (messageExchangeService == null && className.equals(MessageExchangeService.class.getCanonicalName())) {
			messageExchangeService = new MessageExchangeService(networkingFactory.createServer(),
					networkingFactory.createClient());
		}
		return messageExchangeService;
	}
}
