package com.pardxa.chatbox.model;

import com.pardxa.chatbox.network.Client;
import com.pardxa.chatbox.network.LocalHost;
import com.pardxa.chatbox.network.Server;
import com.pardxa.chatbox.network.SocketHolder;
import com.pardxa.chatbox.utils.Constants;
import com.pardxa.chatbox.utils.IMessageHandler;
import java.beans.PropertyChangeListener;
import java.net.InetAddress;
import java.util.Set;

public class MessageExchangeService implements IMessageExchangeService {

  private Server server;
  private Client client;
  private Set<InetAddress> localNicAddrs;

  public MessageExchangeService(Server server, Client client) {
    this.server = server;
    this.client = client;
    localNicAddrs = LocalHost.getNicAddrs();
  }

  @Override
  public void startServer() {
    new Thread(() -> {
      server.startServer();
    }).start();

  }

  public void setMessageHandler(IMessageHandler messageHandler) {
    server.setMessageHandler(messageHandler);
  }

  @Override
  public void sendMessage(InetAddress address, String message) {
    if (!localNicAddrs.contains(address)) {
      client.sendMessage(address, Constants.CONTENT_TYPE_TEXT, message.getBytes());
    }
  }

  @Override
  public void shutdown() {
    SocketHolder.getInstance().clearAllSockets();
    server.stopServer();
  }

  public void addClientPropertyChangeListener(PropertyChangeListener listener) {
    client.addPropertyChangeListener(listener);
  }
}
