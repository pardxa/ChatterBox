package com.pardxa.chatbox.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.pardxa.chatbox.utils.Constants;

public class Client extends AbstractAction {
	public void sendMessage(InetAddress address, byte type, byte[] message) {
		try {
			InetSocketAddress socketAddress=new InetSocketAddress(address,Constants.TCP_PORT);
			if (!SocketHolder.getInstance().hasSocket(socketAddress)) {
				SocketHolder.getInstance().addSocket(socketAddress, new Socket(address, Constants.TCP_PORT));
			}
			Socket socket = SocketHolder.getInstance().getSocket(socketAddress);
			send(socket, Constants.TOKEN, type, message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
