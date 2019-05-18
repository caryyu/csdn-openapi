package com.github.caryyu.openapi.csdn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentContent {
  private List<Comment> sub;
  private Comment info;

  public Comment getInfo() {
    return info;
  }

  public void setInfo(Comment info) {
    this.info = info;
  }

  public List<Comment> getSub() {
    return sub;
  }

  public void setSub(List<Comment> sub) {
    this.sub = sub;
  }
}