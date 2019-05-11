package com.github.caryyu.openapi.csdn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentResult {
  private int result;
  private int vote;
  private String content;
  private CommentData data;

  public int getResult() {
    return result;
  }

  public void setResult(int result) {
    this.result = result;
  }

  public int getVote() {
    return vote;
  }

  public void setVote(int vote) {
    this.vote = vote;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public CommentData getData() {
    return data;
  }

  public void setData(CommentData data) {
    this.data = data;
  }
}
