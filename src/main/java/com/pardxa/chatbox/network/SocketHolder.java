package com.pardxa.chatbox.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SocketHolder {
  private static Map<InetSocketAddress, Socket> sockets = new HashMap<InetSocketAddress, Socket>();
  private final static SocketHolder __instance = new SocketHolder();

  private SocketHolder() {}

  public static SocketHolder getInstance() {
    return __instance;
  }

  public void addSocket(InetSocketAddress address, Socket socket) {
    sockets.put(address, socket);
  }

  public Socket getSocket(InetSocketAddress address) {
    return sockets.get(address);
  }

  public boolean hasSocket(InetSocketAddress address) {
    Set<InetSocketAddress> keys = sockets.keySet();
    return keys.contains(address);
  }

  public void closeSocket(InetSocketAddress address) {
    Socket socket = sockets.get(address);
    try {
      if (socket != null) {
        if (!socket.isClosed()) {
          socket.shutdownInput();
          socket.shutdownOutput();
          socket.close();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void clearAllSockets() {
    sockets.forEach((address, socket) -> {
      closeSocket(address);
    });
    sockets.clear();
  }

}
