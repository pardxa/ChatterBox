package com.pardxa.chatbox.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.pardxa.chatbox.utils.Constants;
import com.pardxa.chatbox.utils.IMessageHandler;

public class Server extends AbstractAction {

	private IMessageHandler messageHandler;
	private ServerSocket server = null;

	public void startServer() {
		try {
			server = new ServerSocket(Constants.TCP_PORT);
			while (!stopLoop) {
				Socket socket = server.accept();
				new Thread(() -> {
					this.receive(socket, messageHandler);
				}).start();
			}
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			try {
				if (server != null || !server.isClosed()) {
					server.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setMessageHandler(IMessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	public void stopServer() {
		stopLoop = true;
		try {
			if (server != null)
				server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
