package com.pardxa.chatbox.utils;

import java.io.InputStream;
import java.net.InetAddress;

public interface IMessageHandler {
  void accept(InputStream inputStream, InetAddress address);
}
