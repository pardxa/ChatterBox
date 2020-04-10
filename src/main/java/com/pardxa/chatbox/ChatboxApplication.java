package com.pardxa.chatbox;

import com.pardxa.chatbox.core.ModelFactory;
import com.pardxa.chatbox.core.NetworkingFactory;
import com.pardxa.chatbox.core.ViewHandler;
import com.pardxa.chatbox.core.ViewModelFactory;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

public class ChatboxApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    NetworkingFactory nf = new NetworkingFactory();
    ModelFactory mf = new ModelFactory(nf);
    ViewModelFactory vmf = new ViewModelFactory(mf);
    ViewHandler vh = new ViewHandler(vmf);
    vh.start(stage);
  }
}
