package com.pardxa.chatbox.model;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.pardxa.chatbox.pojo.MsgInfo;
import com.pardxa.chatbox.pojo.UserInfo;

public class MessageCacheService implements IMessageCacheService {
	private Map<InetAddress, Queue<MsgInfo>> cache = new HashMap<InetAddress, Queue<MsgInfo>>();

	@Override
	public void addUser(UserInfo user) {
		if (!cache.keySet().contains(user.getInetAddress())) {
			// Should add "read from Disk cache"
			Queue<MsgInfo> msgQueue = new ConcurrentLinkedQueue<MsgInfo>();
			cache.put(user.getInetAddress(), msgQueue);
		}
	}

	@Override
	public void removeUser(UserInfo user) {
		// Should add "save to Disk cache"
		cache.remove(user.getInetAddress());
	}

	@Override
	public void addRvMsg(InetAddress idxAddress, String message) {
		Queue<MsgInfo> msgQueue = cache.get(idxAddress);
		msgQueue.add(new MsgInfo(message, new Date().getTime(), idxAddress.getAddress()));
	}

	@Override
	public void addSdMsg(InetAddress srcAddress, InetAddress idxAddress, String message) {
		Queue<MsgInfo> msgQueue = cache.get(idxAddress);
		msgQueue.add(new MsgInfo(message, new Date().getTime(), srcAddress.getAddress()));
	}

	@Override
	public Queue<MsgInfo> getMessageQueue(InetAddress idxAddress) {
		return cache.get(idxAddress);
	}

}
