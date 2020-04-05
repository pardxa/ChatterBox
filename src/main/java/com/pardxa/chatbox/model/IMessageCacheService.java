package com.pardxa.chatbox.model;

import java.net.InetAddress;
import java.util.Queue;

import com.pardxa.chatbox.pojo.MsgInfo;
import com.pardxa.chatbox.pojo.UserInfo;

public interface IMessageCacheService {
	void addUser(UserInfo user);

	void removeUser(UserInfo user);

	void addRvMsg(InetAddress idxAddress, String message);

	void addSdMsg(InetAddress srcAddress, InetAddress idxAddress, String message);

	Queue<MsgInfo> getMessageQueue(InetAddress idxAddress);

	void serialize();

	void deserialzie();
}
