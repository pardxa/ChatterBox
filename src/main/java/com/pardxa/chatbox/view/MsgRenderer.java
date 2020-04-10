package com.pardxa.chatbox.view;

public class MsgRenderer {
  /**
   * Initialize the message display control with empty page with predefined style for send messages
   * and received messages and text locator id="textArea"
   * 
   * @param msg Initial messages
   * @return HTML Page
   */
  public static String initPage(String msg) {
    StringBuffer sb = new StringBuffer("<!DOCTYPE html><html><head>");
    sb.append("<style>");
    sb.append("p{");
    sb.append("font-size:18px;");
    sb.append("border-radius: 8px;");
    sb.append("padding: 10px;");
    sb.append("}");
    sb.append(".send{");
    sb.append("background: #e9e9e9;");
    sb.append("text-align: left;");
    sb.append("margin-left:40%;");
    sb.append("}");
    sb.append(".received{");
    sb.append("color:white;");
    sb.append("background: #00768a;");
    sb.append("margin-right:40%");
    sb.append("}");
    sb.append("</style>");
    sb.append("</head><body><div id='textArea'>");
    sb.append(msg);
    sb.append("</div></body></html>");
    return sb.toString();
  }

  /**
   * To generate JS executive script which could insert input message into Page and scroll down to
   * the newest piece.
   * 
   * @param msg message
   * @return JS executive script
   */
  public static String appendScript(String msg) {
    StringBuffer sb = new StringBuffer("document.getElementById('textArea').innerHTML+='");
    sb.append(msg);
    sb.append("';");
    sb.append("window.scrollTo(0, document.body.scrollHeight);");
    return sb.toString();
  }

  /**
   * To generate JS executive script that clear the message display control
   * 
   * @return JS executive script
   */
  public static String cleanScript() {
    StringBuffer sb = new StringBuffer("document.getElementById('textArea').innerHTML='';");
    return sb.toString();
  }

  public static String receivedMsgScript(String msg) {
    return appendScript(renderMsg(msg.replaceAll("'", "\\\\'"), "received"));
  }

  public static String sendMsgScript(String msg) {
    return appendScript(renderMsg(msg.replaceAll("'", "\\\\'"), "send"));
  }

  public static String renderReceivedMsg(String msg) {
    return renderMsg(msg, "received");
  }

  public static String renderSendMsg(String msg) {
    return renderMsg(msg, "send");
  }

  private static String renderMsg(String msg, String cls) {
    StringBuffer sb = new StringBuffer("<p class=\"" + cls + "\">");
    sb.append(msg);
    sb.append("</p>");
    return sb.toString();
  }

}
