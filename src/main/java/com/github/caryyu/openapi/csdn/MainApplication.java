package com.github.caryyu.openapi.csdn;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainApplication {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static CloseableHttpClient httpclient = HttpClients.createDefault();
    private static List<Header> headers = new ArrayList<Header>();

    public static void main(String[] args) {
        if (login(headers)) {
            System.out.println("登录成功");
            System.out.println(message(headers));
        }
    }

    private static boolean login(List<Header> headers) {
        boolean result = false;
        try {
            String username = System.getenv("USERNAME");
            String password = System.getenv("PASSWORD");

            HttpPost httpPost = new HttpPost("https://passport.csdn.net/v1/register/pc/login/doLogin");
            Map<String, Object> body = new HashMap<String, Object>();
            body.put("loginType", 1);
            body.put("pwdOrVerifyCode", password);
            body.put("userIdentification", username);
            body.put("webUmidToken", "T2AC3D3E115108C5B4E0C42859BE59585B7D1C0F4CEE133699129A3F78B");
            httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(body), ContentType.APPLICATION_JSON));
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

    private static Map<String, Object> message(List<Header> headers) {
        Map<String, Object> result = null;
        try {
            HttpPost httpPost = new HttpPost("https://msg.csdn.net/v1/web/message/view/message");
            httpPost.setHeaders(headers.toArray(new Header[0]));
            Map<String, Object> body = new HashMap<String, Object>();
            body.put("pageIndex", 1);
            body.put("pageSize", 15);
            body.put("type", 0);
            httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(body), ContentType.APPLICATION_JSON));
            CloseableHttpResponse response = httpclient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {
                result = objectMapper.readValue(response.getEntity().getContent(), Map.class);
            }

            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    private static void onlyCookie(List<Header> cookies, Header[] headers) {
        for (Header header : headers) {
            if (header.getName().equals("Set-Cookie")) {
                cookies.add(header);
            }
        }
    }
}