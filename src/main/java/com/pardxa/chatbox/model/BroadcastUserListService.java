package com.pardxa.chatbox.model;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.InetAddress;

import com.google.gson.Gson;
import com.pardxa.chatbox.network.Udp;
import com.pardxa.chatbox.pojo.UserInfo;
import com.pardxa.chatbox.utils.Constants;

public class BroadcastUserListService implements IUserListService {
	private Udp udp;

	public BroadcastUserListService(Udp udp) {
		this.udp = udp;
	}

	@Override
	public void sendOnlineMessage() {
		try {
			udp.broadcastMessage(new Gson().toJson(new UserInfo(Constants.HOSTNAME, UserInfo.statusBroadCast)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendRegisterMessage(InetAddress destAddress) {
		try {
			udp.sendMessage(new Gson().toJson(new UserInfo(Constants.HOSTNAME, UserInfo.statusRegister)), destAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startServer() {
		new Thread(() -> {
			try {
				udp.receiveBroadcast();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	@Override
	public void shutdown() {
		Udp.stopLoop = true;
		try {
			udp.broadcastMessage("EOF");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		udp.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		udp.removePropertyChangeListener(listener);
	}
}
