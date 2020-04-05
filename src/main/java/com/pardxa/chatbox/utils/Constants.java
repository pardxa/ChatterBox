package com.pardxa.chatbox.utils;

public class Constants {
	public final static String HOSTNAME = PropertyLoader.load("HOSTNAME");
	public final static int TCP_PORT = Integer.parseInt(PropertyLoader.load("TCPPORT"));
	public final static int UDP_PORT = Integer.parseInt(PropertyLoader.load("UDPPORT"));
	public final static byte CONTENT_TYPE_TEXT = 0;
	public final static byte CONTENT_TYPE_IMAGE = 1;
	public final static byte CONTENT_TYPE_ANY = 5;
	public final static byte TOKEN = 11;
	public final static String USER_LIST_CLASS_NAME = PropertyLoader.load("USERLISTSERVICE");
	public final static String MESSAGE_EXCHANGE_CLASS_NAME = "com.pardxa.chatbox.model.MessageExchangeService";
	public final static String EMPTY_STRING = "";
	public final static String CACHE_FILE = "chatbox.cache";
}
