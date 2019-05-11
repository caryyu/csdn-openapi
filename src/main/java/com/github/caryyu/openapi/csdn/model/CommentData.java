package com.github.caryyu.openapi.csdn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentData {
  private int count;
  @JsonProperty("page_count")
  private int pageCount;
  @JsonProperty("floor_count")
  private int floorCount;
  private List<CommentContent> list;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getPageCount() {
    return pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  public int getFloorCount() {
    return floorCount;
  }

  public void setFloorCount(int floorCount) {
    this.floorCount = floorCount;
  }

  public List<CommentContent> getList() {
    return list;
  }

  public void setList(List<CommentContent> list) {
    this.list = list;
  }
}
