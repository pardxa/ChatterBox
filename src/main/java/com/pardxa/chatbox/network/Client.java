package com.pardxa.chatbox.network;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import com.pardxa.chatbox.utils.Constants;

public class Client extends AbstractAction {
	protected PropertyChangeSupport support;

	public Client() {
		support = new PropertyChangeSupport(this);
	}

	public void sendMessage(InetAddress address, byte type, byte[] message) {
		try {
			InetSocketAddress socketAddress = new InetSocketAddress(address, Constants.TCP_PORT);
			if (!SocketHolder.getInstance().hasSocket(socketAddress)) {
				SocketHolder.getInstance().addSocket(socketAddress, new Socket(address, Constants.TCP_PORT));
			}
			Socket socket = SocketHolder.getInstance().getSocket(socketAddress);
			send(socket, Constants.TOKEN, type, message);
		} catch (SocketException e) {
			this.support.firePropertyChange(Constants.CONNECT_REFUSED, null, address);
			SocketHolder.getInstance().closeSocket(new InetSocketAddress(address, Constants.TCP_PORT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
}
