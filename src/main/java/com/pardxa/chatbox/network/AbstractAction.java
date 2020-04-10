package com.pardxa.chatbox.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import com.pardxa.chatbox.utils.IMessageHandler;

public abstract class AbstractAction {
  public static volatile boolean stopLoop = false;

  public void send(Socket socket, byte token, byte type, byte[] message) {
    try {
      OutputStream output = socket.getOutputStream();
      byte[] tokenArray = {token};
      byte[] typeArray = {type};
      output.write(tokenArray, 0, tokenArray.length);
      output.write(typeArray, 0, typeArray.length);
      output.write(message, 0, message.length);
      output.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void receive(Socket socket, IMessageHandler messageHandler) {
    BufferedInputStream inputStream = null;
    try {
      // InetSocketAddress address = new InetSocketAddress(socket.getInetAddress(),
      // socket.getLocalPort());
      // SocketHolder.getInstance().addSocket(address, socket);
      inputStream = new BufferedInputStream(socket.getInputStream());
      byte[] token = new byte[1];
      do {
        int result = inputStream.read(token, 0, token.length);
        if (result < 0) {
          break;
        }
        messageHandler.accept(inputStream, socket.getInetAddress());
      } while (true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
