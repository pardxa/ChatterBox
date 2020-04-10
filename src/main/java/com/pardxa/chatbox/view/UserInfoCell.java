package com.pardxa.chatbox.view;

import com.pardxa.chatbox.pojo.UserInfo;
import javafx.scene.control.ListCell;

public class UserInfoCell extends ListCell<UserInfo> {
  public void updateItem(UserInfo item, boolean empty) {
    super.updateItem(item, empty);
    if (item != null) {
      setText(item.getUserName());
    }
    /*
     * If the cell is empty or the item is null, you need to set the text to an empty string (or
     * null), and reset the styles to the default. you can do all this directly in CSS, without the
     * need for a ListCell subclass at all
     */
    if (item == null || empty) {
      setText(null);
    }
  }
}
