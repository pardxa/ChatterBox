package com.pardxa.chatbox.model;

import com.pardxa.chatbox.utils.IMessageHandler;
import java.beans.PropertyChangeListener;
import java.net.InetAddress;

public interface IMessageExchangeService extends INetworkService {
  void sendMessage(InetAddress address, String message);

  void startServer();

  void setMessageHandler(IMessageHandler messageHandler);

  void addClientPropertyChangeListener(PropertyChangeListener listener);
}
