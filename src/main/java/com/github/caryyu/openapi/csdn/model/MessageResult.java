package com.github.caryyu.openapi.csdn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageResult {
  private int code;
  private String message;
  private MessageData data;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public MessageData getData() {
    return data;
  }

  public void setData(MessageData data) {
    this.data = data;
  }
}
