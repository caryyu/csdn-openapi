package com.github.caryyu.openapi.csdn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.caryyu.openapi.csdn.model.ClearMessageResult;
import com.github.caryyu.openapi.csdn.model.CommentResult;
import com.github.caryyu.openapi.csdn.model.MessageResult;
import org.apache.http.Header;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

public class Apis {
  private final String API_URL_LOGIN = "https://passport.csdn.net/v1/register/pc/login/doLogin";
  private final String API_URL_MESSAGES = "https://msg.csdn.net/v1/web/message/view/message";
  private final String API_URL_COMMENTS = "https://blog.csdn.net/%s/phoenix/comment/list/%s";
  private final String API_URL_CLEAR_MSG = "https://msg.csdn.net/v1/web/message/read";
  private final String API_URL_COMMENT_ADD =
      "https://blog.csdn.net/%s/phoenix/comment/submit?id=%s";
  private final String API_URL_COMMENT_DEL = "https://blog.csdn.net/%s/phoenix/comment/delete";

  private ObjectMapper objectMapper;
  private CloseableHttpClient httpclient;
  private List<Header> cookies;

  public Apis() {
    objectMapper = new ObjectMapper();
    httpclient = HttpClients.createDefault();
    cookies = new ArrayList<Header>();

    getUsername();
    getPassword();
  }

  /**
   * 登陆接口
   *
   * @param headers 成功之后接收 Cookie 使用
   * @return boolean
   */
  private boolean login(List<Header> headers) {
    boolean result = false;
    try {
      String username = getUsername();
      String password = getPassword();

      HttpPost httpPost = new HttpPost(API_URL_LOGIN);
      Map<String, Object> body = new HashMap<String, Object>();
      body.put("loginType", 1);
      body.put("pwdOrVerifyCode", password);
      body.put("userIdentification", username);
      body.put("webUmidToken", "T2AC3D3E115108C5B4E0C42859BE59585B7D1C0F4CEE133699129A3F78B");
      httpPost.setEntity(
          new StringEntity(objectMapper.writeValueAsString(body), ContentType.APPLICATION_JSON));
      CloseableHttpResponse response = httpclient.execute(httpPost);
      result = response.getStatusLine().getStatusCode() == 200;
      onlyCookie(headers, response.getAllHeaders());
      response.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      return result;
    }
  }

  public MessageResult messages(int page, int size) {
    List<Header> cookies = checkCookies();
    MessageResult result = null;
    try {
      HttpPost httpPost = new HttpPost(API_URL_MESSAGES);
      httpPost.setHeaders(cookies.toArray(new Header[0]));
      Map<String, Object> body = new HashMap<String, Object>();
      body.put("pageIndex", page);
      body.put("pageSize", size);
      body.put("type", 0);
      httpPost.setEntity(
          new StringEntity(objectMapper.writeValueAsString(body), ContentType.APPLICATION_JSON));
      CloseableHttpResponse response = httpclient.execute(httpPost);

      if (response.getStatusLine().getStatusCode() == 200) {
        result = objectMapper.readValue(response.getEntity().getContent(), MessageResult.class);
      }

      response.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      return result;
    }
  }

  public CommentResult comments(String id, int page, int size) {
    List<Header> cookies = checkCookies();
    CommentResult result = null;
    try {
      URI uri =
          new URIBuilder(String.format(API_URL_COMMENTS, getUsername(), id))
              .addParameter("page", String.valueOf(page))
              .addParameter("size", String.valueOf(size))
              .addParameter("tree_type", "1")
              .build();
      HttpGet httpGet = new HttpGet(uri);
      httpGet.setHeaders(cookies.toArray(new Header[0]));
      CloseableHttpResponse response = httpclient.execute(httpGet);
      if (response.getStatusLine().getStatusCode() == 200) {
        result = objectMapper.readValue(response.getEntity().getContent(), CommentResult.class);
      }
      response.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      return result;
    }
  }

  public ClearMessageResult clearMessage(String id) {
    List<Header> cookies = checkCookies();
    ClearMessageResult result = null;
    try {
      HttpPost request = new HttpPost(API_URL_CLEAR_MSG);
      request.setHeaders(cookies.toArray(new Header[0]));
      Map<String, Object> body = new HashMap<String, Object>();
      body.put("id", id);
      body.put("type", 0);
      request.setEntity(
          new StringEntity(objectMapper.writeValueAsString(body), ContentType.APPLICATION_JSON));
      CloseableHttpResponse response = httpclient.execute(request);
      if (response.getStatusLine().getStatusCode() == 200) {
        result =
            objectMapper.readValue(response.getEntity().getContent(), ClearMessageResult.class);
      }
      response.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      return result;
    }
  }

  /**
   * 发表评论
   *
   * @param id 文章编号
   * @param content 内容
   * @return
   */
  public int comment(String id, String content) {
    List<Header> cookies = checkCookies();
    try {
      HttpPost request = new HttpPost(String.format(API_URL_COMMENT_ADD, getUsername(), id));
      request.setHeaders(cookies.toArray(new Header[0]));
      request.setEntity(
          new UrlEncodedFormEntity(
              Arrays.asList(new BasicNameValuePair("content", content)), Charset.defaultCharset()));
      CloseableHttpResponse response = httpclient.execute(request);
      if (response.getStatusLine().getStatusCode() == 200) {
        Map result = objectMapper.readValue(response.getEntity().getContent(), Map.class);
        return Integer.parseInt(result.get("data").toString());
      }
      response.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1;
  }

  /**
   * 回复评论
   *
   * @param id 文章编号
   * @param replyId 评论编号
   * @param content 内容
   * @return
   */
  public int reply(String id, String replyId, String content) {
    List<Header> cookies = checkCookies();
    try {
      HttpPost request = new HttpPost(String.format(API_URL_COMMENT_ADD, getUsername(), id));
      request.setHeaders(cookies.toArray(new Header[0]));
      request.setEntity(
          new UrlEncodedFormEntity(
              Arrays.asList(
                  new BasicNameValuePair("replyId", replyId),
                  new BasicNameValuePair("content", content)),
              Charset.defaultCharset()));
      CloseableHttpResponse response = httpclient.execute(request);
      if (response.getStatusLine().getStatusCode() == 200) {
        Map result = objectMapper.readValue(response.getEntity().getContent(), Map.class);
        return Integer.parseInt(result.get("data").toString());
      }
      response.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1;
  }

  public boolean delComment(String id, String commentId) {
    List<Header> cookies = checkCookies();
    try {
      HttpPost request = new HttpPost(String.format(API_URL_COMMENT_DEL, getUsername()));
      request.setHeaders(cookies.toArray(new Header[0]));
      request.setEntity(
          new UrlEncodedFormEntity(
              Arrays.asList(
                  new BasicNameValuePair("filename", id),
                  new BasicNameValuePair("commentId", commentId)),
              Charset.defaultCharset()));
      CloseableHttpResponse response = httpclient.execute(request);
      if (response.getStatusLine().getStatusCode() == 200) {
        Map result = objectMapper.readValue(response.getEntity().getContent(), Map.class);
        return Integer.parseInt(result.get("result").toString()) == 1;
      }
      response.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  private List<Header> checkCookies() {
    if (cookies == null || cookies.isEmpty()) {
      if (!login(cookies)) {
        throw new RuntimeException("Username or password invalid");
      }
    }
    return cookies;
  }

  private static void onlyCookie(List<Header> cookies, Header[] headers) {
    for (Header header : headers) {
      if (header.getName().equals("Set-Cookie")) {
        cookies.add(header);
      }
    }
  }

  private String getUsername() {
    String username = System.getenv("USERNAME");
    if (username == null) {
      throw new NullPointerException("Username not found");
    }
    return username;
  }

  private String getPassword() {
    String password = System.getenv("PASSWORD");
    if (password == null) {
      throw new NullPointerException("Password not found");
    }
    return password;
  }
}
