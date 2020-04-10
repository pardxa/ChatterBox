module com.pardxa.chatbox {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires com.google.gson;
  requires javafx.base;
  requires transitive javafx.graphics;
  requires transitive java.desktop;
  requires javafx.web;

  opens com.pardxa.chatbox.view to javafx.fxml;

  exports com.pardxa.chatbox.view;
  exports com.pardxa.chatbox.network;
  exports com.pardxa.chatbox.pojo;
  exports com.pardxa.chatbox;
  exports com.pardxa.chatbox.model;
  exports com.pardxa.chatbox.utils;
}
