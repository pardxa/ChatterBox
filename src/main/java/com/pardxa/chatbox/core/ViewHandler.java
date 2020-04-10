package com.pardxa.chatbox.core;

import com.pardxa.chatbox.view.IStageAware;
import com.pardxa.chatbox.view.UIController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewHandler {
  private ViewModelFactory viewModelFactory;
  private UIController uiController;
  private static Scene scene;

  public ViewHandler(ViewModelFactory viewModelFactory) {
    this.viewModelFactory = viewModelFactory;
  }

  public void start(Stage stage) {
    FXMLLoader fxmlLoader =
        new FXMLLoader(getClass().getResource("/com/pardxa/chatbox/view/ui.fxml"));
    try {
      scene = new Scene(fxmlLoader.load(), 640, 480);
    } catch (IOException e) {
      e.printStackTrace();
    }
    uiController = fxmlLoader.getController();
    if (uiController instanceof IStageAware) {
      uiController.setStage(stage);
    }
    uiController.init(viewModelFactory.createUIViewModel());
    stage.setOnHidden(event -> {
      uiController.stop();
    });
    stage.setScene(scene);
    stage.show();
  }
}
