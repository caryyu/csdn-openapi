package com.github.caryyu.openapi.csdn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
  @JsonProperty("CommentId")
  private String commentId;
  @JsonProperty("ArticleId")
  private String articleId;
  @JsonProperty("BlogId")
  private String blogId;
  @JsonProperty("PostTime")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
  private Date postTime;
  @JsonProperty("UserName")
  private String username;
  @JsonProperty("Avatar")
  private String avatar;
  @JsonProperty("NickName")
  private String nickname;
  @JsonProperty("Content")
  private String content;
  @JsonProperty("ParentId")
  private String parentId;
  @JsonProperty("IP")
  private String ip;

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

  public String getArticleId() {
    return articleId;
  }

  public void setArticleId(String articleId) {
    this.articleId = articleId;
  }

  public String getBlogId() {
    return blogId;
  }

  public void setBlogId(String blogId) {
    this.blogId = blogId;
  }

  public Date getPostTime() {
    return postTime;
  }

  public void setPostTime(Date postTime) {
    this.postTime = postTime;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }
}
