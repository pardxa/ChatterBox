package com.pardxa.chatbox.model;

import java.beans.PropertyChangeListener;
import java.net.InetAddress;

public interface IUserListService extends INetworkService {

	void sendOnlineMessage();

	void sendRegisterMessage(InetAddress destAddress);

	public void addPropertyChangeListener(PropertyChangeListener listener);

	public void removePropertyChangeListener(PropertyChangeListener listener);

}