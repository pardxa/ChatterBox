package com.pardxa.chatbox.pojo;

import java.io.Serializable;

public class MsgInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String message;
	private long time;
	private byte[] address;
	
	public MsgInfo() {}
	public MsgInfo(String message,long time,byte[] address) {
		this.message=message;
		this.time=time;
		this.address=address;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public byte[] getAddress() {
		return address;
	}
	public void setAddress(byte[] address) {
		this.address = address;
	}
}
