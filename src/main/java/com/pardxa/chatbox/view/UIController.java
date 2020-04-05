package com.pardxa.chatbox.view;

import com.pardxa.chatbox.pojo.UserInfo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class UIController {
	@FXML
	private ListView<UserInfo> listView;
	@FXML
	private WebView messageView;
	@FXML
	private TextField textField;
	private UIViewModel uiViewModel;
	private WebEngine engine;

	public void init(UIViewModel uiViewModel) {
		this.uiViewModel = uiViewModel;
		listView.setCellFactory(lv -> new UserInfoCell());
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listView.setItems(uiViewModel.itemListProperty());
		engine = messageView.getEngine();
		engine.loadContent(MsgRenderer.initPage(""));
		this.uiViewModel.initUserList();
		this.uiViewModel.initMessageHandler();
		this.uiViewModel.setReceivedTextPrinter(receivedMessage -> {
			Platform.runLater(() -> {
				String script = MsgRenderer.receivedMsgScript(receivedMessage);
				engine.executeScript(script);
			});
		});
		this.uiViewModel.setSendTextPrinter(sendMessage -> {
			Platform.runLater(() -> {
				String script = MsgRenderer.sendMsgScript(sendMessage);
				engine.executeScript(script);
			});
		});
		this.uiViewModel.getInputText().bindBidirectional(this.textField.textProperty());
	}

	public void stop() {
		uiViewModel.stop();
	}

	@FXML
	private void OnSend(ActionEvent evt) {
		sendMessage();
	}

	@FXML
	private void OnEnter(ActionEvent evt) {
		sendMessage();
	}

	private void sendMessage() {
		int idx = listView.getSelectionModel().getSelectedIndex();
		if (idx > -1) {
			uiViewModel.sendMessage(idx);
		}
	}

	public void initialize() {

//		listView.setCellFactory(new Callback<ListView<UserInfo>,ListCell<UserInfo>>(){
//			@Override
//			public ListCell<UserInfo> call(ListView<UserInfo> param) {
//				return new UserInfoCell();
//			}			
//		});
	}
}
