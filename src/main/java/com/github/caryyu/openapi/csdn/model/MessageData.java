package com.github.caryyu.openapi.csdn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageData {
  private int countNum;
  private int unReadCount;
  private List<Message> resultList;

  public int getCountNum() {
    return countNum;
  }

  public void setCountNum(int countNum) {
    this.countNum = countNum;
  }

  public int getUnReadCount() {
    return unReadCount;
  }

  public void setUnReadCount(int unReadCount) {
    this.unReadCount = unReadCount;
  }

  public List<Message> getResultList() {
    return resultList;
  }

  public void setResultList(List<Message> resultList) {
    this.resultList = resultList;
  }
}
