package com.github.caryyu.openapi.csdn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentContent {
  private Comment info;

  public Comment getInfo() {
    return info;
  }

  public void setInfo(Comment info) {
    this.info = info;
  }
}
