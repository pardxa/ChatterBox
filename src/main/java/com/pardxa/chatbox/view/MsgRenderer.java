package com.pardxa.chatbox.view;

public class MsgRenderer {
  public static String initPage(String msg) {
    StringBuffer sb = new StringBuffer("<!DOCTYPE html><html><head>");
    sb.append("<style>");
    sb.append("p{");
    sb.append("font-size:20px;");
    sb.append("border-radius: 8px;");
    sb.append("padding: 10px");
    sb.append("}");
    sb.append("</style>");
    sb.append("</head><body><div id='textArea'>");
    sb.append(msg);
    sb.append("</div></body></html>");
    return sb.toString();
  }

  public static String appendScript(String msg) {
    StringBuffer sb = new StringBuffer("document.getElementById('textArea').innerHTML+='");
    sb.append(msg);
    sb.append("';");
    sb.append("window.scrollTo(0, document.body.scrollHeight);");
    return sb.toString();
  }

  public static String cleanScript() {
    StringBuffer sb = new StringBuffer("document.getElementById('textArea').innerHTML='';");
    return sb.toString();
  }

  public static String receivedMsgScript(String msg) {
    return appendScript(renderReceivedMsg(msg.replaceAll("'", "\\\\'")));
  }

  public static String sendMsgScript(String msg) {
    return appendScript(renderSendMsg(msg.replaceAll("'", "\\\\'")));
  }

  public static String renderReceivedMsg(String msg) {
    StringBuffer sb =
        new StringBuffer("<p style=\"color:white;background: #00768a;margin-right:40%\">");
    sb.append(msg);
    sb.append("</p>");
    return sb.toString();
  }

  public static String renderSendMsg(String msg) {
    StringBuffer sb =
        new StringBuffer("<p style=\"background: #e9e9e9;text-align: left;margin-left:40%\">");
    sb.append(msg);
    sb.append("</p>");
    return sb.toString();
  }
}
