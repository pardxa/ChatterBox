package com.pardxa.chatbox.model;

import java.net.InetAddress;
import com.pardxa.chatbox.pojo.MsgInfo;
import java.util.Queue;


public interface IMessageCacheService {
  void addUser(InetAddress address);

  void removeUser(InetAddress address);

  void addRvMsg(InetAddress idxAddress, String message);

  void addSdMsg(InetAddress srcAddress, InetAddress idxAddress, String message);

  Queue<MsgInfo> getMessageQueue(InetAddress idxAddress);

  void serialize();

  void deserialzie();
}
