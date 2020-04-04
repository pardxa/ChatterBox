package com.pardxa.chatbox.model;

import java.net.InetAddress;

import com.pardxa.chatbox.utils.IMessageHandler;

public interface IMessageExchangeService extends INetworkService {
	void sendMessage(InetAddress address,String message);
	void startServer();
	void setMessageHandler(IMessageHandler messageHandler);
}
