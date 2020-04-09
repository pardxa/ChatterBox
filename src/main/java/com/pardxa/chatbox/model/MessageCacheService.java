package com.pardxa.chatbox.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.pardxa.chatbox.pojo.MsgInfo;
import com.pardxa.chatbox.utils.Constants;

public class MessageCacheService implements IMessageCacheService {
	private Map<InetAddress, Queue<MsgInfo>> cache = new HashMap<InetAddress, Queue<MsgInfo>>();

	@Override
	public void addUser(InetAddress address) {
		if (!cache.keySet().contains(address)) {
			// Should add "read from Disk cache"
			Queue<MsgInfo> msgQueue = new ConcurrentLinkedQueue<MsgInfo>();
			cache.put(address, msgQueue);
		}
	}

	@Override
	public void removeUser(InetAddress address) {
		// Should add "save to Disk cache"
		cache.remove(address);
	}

	@Override
	public void addRvMsg(InetAddress idxAddress, String message) {
		Queue<MsgInfo> msgQueue = cache.get(idxAddress);
		msgQueue.add(new MsgInfo(message, new Date().getTime(), idxAddress.getAddress()));
	}

	@Override
	public void addSdMsg(InetAddress srcAddress, InetAddress idxAddress, String message) {
		Queue<MsgInfo> msgQueue = cache.get(idxAddress);
		if(msgQueue!=null) {
			msgQueue.add(new MsgInfo(message, new Date().getTime(), srcAddress.getAddress()));
		}		
	}

	@Override
	public Queue<MsgInfo> getMessageQueue(InetAddress idxAddress) {
		return cache.get(idxAddress);
	}

	@Override
	public void serialize() {
		try {
			File file = new File(Constants.CACHE_FILE);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fouts = new FileOutputStream(file);
			ObjectOutputStream objOuts = new ObjectOutputStream(fouts);
			objOuts.writeObject(cache);
			objOuts.flush();
			objOuts.close();
			fouts.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deserialzie() {
		try {
			File file = new File(Constants.CACHE_FILE);
			if (file.exists()) {
				FileInputStream fins = new FileInputStream(file);
				ObjectInputStream objIns = new ObjectInputStream(fins);
				cache = (Map<InetAddress, Queue<MsgInfo>>) objIns.readObject();
				objIns.close();
				fins.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
