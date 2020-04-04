package com.pardxa.chatbox.core;

import com.pardxa.chatbox.network.Client;
import com.pardxa.chatbox.network.Server;
import com.pardxa.chatbox.network.Udp;

public class NetworkingFactory {
	private Udp udp;
	private Server server;
	private Client client;

	public Udp createUdp() {
		if (udp == null) {
			udp = new Udp();
		}
		return udp;
	}
	public Server createServer() {
		if(server==null) {
			server=new Server();
		}
		return server;
	}
	public Client createClient() {
		if(client==null) {
			client=new Client();
		}
		return client;
	}
}
