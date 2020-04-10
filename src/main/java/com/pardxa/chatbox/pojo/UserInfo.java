package com.pardxa.chatbox.pojo;

import java.net.InetAddress;

public class UserInfo {
  public final static int statusBroadCast = 1;
  public final static int statusRegister = 2;
  private String userName;
  private InetAddress inetAddress;
  private int status;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public InetAddress getInetAddress() {
    return inetAddress;
  }

  public void setInetAddress(InetAddress inetAddress) {
    this.inetAddress = inetAddress;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public UserInfo(String userName) {
    this.userName = userName;
  }

  public UserInfo(String userName, int status) {
    this.userName = userName;
    this.status = status;
  }
}
