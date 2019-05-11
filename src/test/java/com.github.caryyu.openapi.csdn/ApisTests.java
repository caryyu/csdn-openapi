package com.github.caryyu.openapi.csdn;

import com.github.caryyu.openapi.csdn.model.ClearMessageResult;
import com.github.caryyu.openapi.csdn.model.CommentResult;
import com.github.caryyu.openapi.csdn.model.Message;
import com.github.caryyu.openapi.csdn.model.MessageResult;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class ApisTests {
  private static Apis apis;

  @BeforeClass
  public static void tearUp() {
    apis = new Apis();
  }

  @Test
  public void testMessages() {
    MessageResult result = apis.messages(1, 15);
    assert "success".equals(result.getMessage());
  }

  @Test
  public void testComments() {
    MessageResult result = apis.messages(1, 15);
    List<Message> list = result.getData().getResultList();
    if (list == null || list.isEmpty()) {
      assert true;
    } else {
      String id = String.valueOf(list.get(0).getContent().getId());
      CommentResult cResult = apis.comments(id,1,15);
      assert "success".equals(cResult.getContent());
    }
  }

  @Test
  public void testClearMessage() {
    MessageResult result = apis.messages(1, 15);
    List<Message> list = result.getData().getResultList();
    if (list == null || list.isEmpty()) {
      assert true;
    } else {
      String id = String.valueOf(list.get(0).getId());
      ClearMessageResult cmResult = apis.clearMessage(id);
      assert "success".equals(cmResult.getMessage());
    }
  }
}