package com.pardxa.chatbox.network;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Set;
import com.google.gson.Gson;
import com.pardxa.chatbox.pojo.UserInfo;
import com.pardxa.chatbox.utils.Constants;

public class Udp {

  public static volatile boolean stopLoop = false;
  private PropertyChangeSupport support;
  private Set<InetAddress> localNicAddrs;

  public Udp() {
    support = new PropertyChangeSupport(this);
    localNicAddrs = LocalHost.getNicAddrs();
  }

  public void broadcastMessage(String message) throws IOException {
    List<InetAddress> addrs = LocalHost.getNicBroadcastAddr();
    for (InetAddress address : addrs) {
      sendMessage(message, address, Constants.UDP_PORT, true);
    }
    if (!"EOF".equals(message)) {
      UserInfo userInfo = new UserInfo(new Gson().fromJson(message, UserInfo.class).getUserName());
      userInfo.setInetAddress(LocalHost.getLocalHost());
      support.firePropertyChange("", null, userInfo);
    }
  }

  public void sendMessage(String message, InetAddress destAddress) throws IOException {
    sendMessage(message, destAddress, Constants.UDP_PORT, false);
  }

  //
  public void sendMessage(String message, InetAddress destAddress, int port, boolean isBroadcast)
      throws IOException {
    DatagramSocket socket = new DatagramSocket();
    socket.setBroadcast(isBroadcast);
    byte[] buffer = message.getBytes();
    DatagramPacket pak = new DatagramPacket(buffer, buffer.length, destAddress, port);
    socket.send(pak);
    socket.close();
  }

  public void receiveBroadcast() throws IOException {
    receiveMessage(InetAddress.getByName("0.0.0.0"), Constants.UDP_PORT);
  }

  public void receiveMessage(InetAddress sourceAddress, int port) {
    DatagramSocket socket = null;
    try {
      socket = new DatagramSocket(port, sourceAddress);
      byte[] buffer = new byte[1024 * 8];
      DatagramPacket pak = new DatagramPacket(buffer, buffer.length);
      while (!stopLoop) {
        socket.receive(pak);
        String responseString = new String(buffer, 0, pak.getLength());
        if ("EOF".equals(responseString)) {
          if (!localNicAddrs.contains(pak.getAddress())) {
            UserInfo userInfo = new UserInfo("");
            userInfo.setInetAddress(pak.getAddress());
            support.firePropertyChange("EOF", null, userInfo);
          }
          continue;
        }
        if (!localNicAddrs.contains(pak.getAddress())) {
          UserInfo userInfo = new Gson().fromJson(responseString, UserInfo.class);
          userInfo.setInetAddress(pak.getAddress());
          support.firePropertyChange("", null, userInfo);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (socket != null) {
        socket.close();
      }
    }
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    support.removePropertyChangeListener(listener);
  }
}
