package com.pardxa.chatbox.view;

import java.util.Arrays;
import java.util.Queue;

import com.pardxa.chatbox.pojo.MsgInfo;
import com.pardxa.chatbox.pojo.UserInfo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class UIController implements IStageAware {
	@FXML
	private ListView<UserInfo> listView;
	@FXML
	private WebView messageView;
	@FXML
	private TextField textField;
	private Stage stage;
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
		this.uiViewModel.inputTextProperty().bindBidirectional(this.textField.textProperty());
		this.listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			appendMessage(newValue);
			setStageTitle();
		});

		this.uiViewModel.currentUserIdxProperty().bind(listView.getSelectionModel().selectedIndexProperty());
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
		/**
		 * listView.setCellFactory(new
		 * Callback<ListView<UserInfo>,ListCell<UserInfo>>(){
		 * 
		 * @Override public ListCell<UserInfo> call(ListView<UserInfo> param) { return
		 *           new UserInfoCell(); } });
		 */
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void appendMessage(UserInfo userInfo) {
		String script = MsgRenderer.cleanScript();
		engine.executeScript(script);
		Queue<MsgInfo> messagesInfos = this.uiViewModel.getMessageQueue(userInfo);
		StringBuffer sb = new StringBuffer();
		messagesInfos.forEach(msg -> {
			byte[] address = msg.getAddress();
			byte[] indexAddress = userInfo.getInetAddress().getAddress();
			if (Arrays.equals(address, indexAddress)) {
				sb.append(MsgRenderer.renderReceivedMsg(msg.getMessage()));
			} else {
				sb.append(MsgRenderer.renderSendMsg(msg.getMessage()));
			}
		});
		String newScript = MsgRenderer.appendScript(sb.toString());
		engine.executeScript(newScript);
	}

	private void setStageTitle() {
		UserInfo user = listView.getSelectionModel().getSelectedItem();
		StringBuffer title = new StringBuffer(user.getUserName());
		title.append(":");
		title.append(user.getInetAddress().getHostAddress());
		stage.setTitle(title.toString());
	}
}
