package com.pardxa.chatbox.view;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Queue;
import java.util.function.Consumer;

import com.pardxa.chatbox.model.IMessageCacheService;
import com.pardxa.chatbox.model.IMessageExchangeService;
import com.pardxa.chatbox.model.IUserListService;
import com.pardxa.chatbox.network.LocalHost;
import com.pardxa.chatbox.pojo.MsgInfo;
import com.pardxa.chatbox.pojo.UserInfo;
import com.pardxa.chatbox.utils.Constants;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UIViewModel {
	private IUserListService userListService;
	private IMessageExchangeService messageExchangeService;
	private IMessageCacheService messageCacheService;
	private StringProperty inputText;
	private ObservableList<UserInfo> itemList;
	private Consumer<String> sendTextPrinter;
	private Consumer<String> receivedTextPrinter;

	public UIViewModel(IUserListService userListService, IMessageExchangeService messageExchangeService,
			IMessageCacheService messageCacheService) {
		this.userListService = userListService;
		this.messageExchangeService = messageExchangeService;
		this.messageCacheService = messageCacheService;
		itemList = FXCollections.observableArrayList();
		inputText = new SimpleStringProperty();
	}

	public ObservableList<UserInfo> itemListProperty() {
		return itemList;
	}

	public StringProperty getInputText() {
		return inputText;
	}

	public void sendMessage(int selectedIndex) {
		String message = inputText.get();
		if (!Constants.EMPTY_STRING.equals(message.trim())) {
			sendTextPrinter.accept(message);
			InetAddress idxAddress = itemList.get(selectedIndex).getInetAddress();
			messageExchangeService.sendMessage(idxAddress, message);
			inputText.set(Constants.EMPTY_STRING);
			messageCacheService.addSdMsg(LocalHost.getLocalHost(), idxAddress, message);
		}
	}

	public void initUserList() {
		userListService.addPropertyChangeListener(event -> {
			UserInfo userInfo = (UserInfo) event.getNewValue();
			if (!"EOF".equals(event.getPropertyName())) {
				Platform.runLater(() -> {
					itemList.add(userInfo);
				});
				messageCacheService.addUser(userInfo);
				if (userInfo.getStatus() == UserInfo.statusBroadCast) {
					userListService.sendRegisterMessage(userInfo.getInetAddress());
				}
			} else {
				messageCacheService.removeUser(userInfo);
				Platform.runLater(() -> {
					itemList.removeIf(uif -> (userInfo.getInetAddress().equals(uif.getInetAddress()) ? true : false));
				});
			}
		});
		userListService.startServer();
		userListService.sendOnlineMessage();
	}

	public void initMessageHandler() {
		messageExchangeService.setMessageHandler((inputStream, address) -> {

			try {
				BufferedInputStream buffStream = (BufferedInputStream) inputStream;
				int token = buffStream.read();
				if (token == Constants.CONTENT_TYPE_TEXT) {
					//
					byte[] content = new byte[2048];
					int result = buffStream.read(content, 0, content.length);
					String line = new String(content, 0, result);
					receivedTextPrinter.accept(line);
					messageCacheService.addRvMsg(address, line);
					//
				}
				if (token == Constants.CONTENT_TYPE_IMAGE) {
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		messageExchangeService.startServer();
	}
	public Queue<MsgInfo> getMessageQueue(UserInfo userinfo){
		return messageCacheService.getMessageQueue(userinfo.getInetAddress());
	}
	public void setSendTextPrinter(Consumer<String> sendTextPrinter) {
		this.sendTextPrinter = sendTextPrinter;
	}

	public void setReceivedTextPrinter(Consumer<String> receivedTextPrinter) {
		this.receivedTextPrinter = receivedTextPrinter;
	}

	public void stop() {
		userListService.shutdown();
		messageExchangeService.shutdown();
	}
}
