# csdn-openapi
一个封装 CSDN 接口的 Java 第三方程序，目前支持的功能参考下面的列表，有兴趣的有伙伴可以一起追加维护哈！😄
> 注意：这些接口并非官方开放平台所提供的，均是作者分析 CSDN 页面的调用而得到的，存在接口被禁用的风险，请谨慎使用！

* 获取消息列表
* 标记消息已读
* 文章的评论列表
* 对文章发表评论
* 对评论进行回复
* 删除某条评论

## Required
* Maven 3
* JDK 8

## 已知问题
* 由于 CSDN 有接入阿里云的风险识别,所以目前此依赖包只能在本地使用,部署到服务端会出现一些问题.

## Usage

### 环境变量
| 变量名 | 是否必填 | 说明 |
| ---- | ---- | ---- |
| **USERNAME** | 必填 | 你的 CSDN 登陆账号 |
| **PASSWORD** | 必填 | 你的 CSDN 登陆密码 |

### 未读消息
```
    Apis apis = new Apis();
    MessageResult result = apis.messages(1, 15);
    List<Message> list = result.getData().getResultList();
    for (Message msg : list) {
      if (msg.isUnread()) {
        System.out.println(msg.getContent());
      }
    }
```

## API
### Login
POST https://passport.csdn.net/v1/register/pc/login/doLogin
```
{
    "loginType": 1,
    "pwdOrVerifyCode": "",
    "userIdentification": "",
    "webUmidToken": "T2AC3D3E115108C5B4E0C42859BE59585B7D1C0F4CEE133699129A3F78B"
}
```

### Message
POST https://msg.csdn.net/v1/web/message/view/message
```
{
	"pageIndex": 1,
	"pageSize": 15,
	"type": 0
}
```

Response
```
{
    "code": "0",
    "message": "success",
    "data": {
        "countNum": 7,
        "hasUnRead": {
            "0": 0,
            "1": 0,
            "2": 0,
            "3": 0,
            "4": 0
        },
        "unReadCount": 1,
        "resultList": [
            {
                "id": 518702,
                "time": "2019-05-05",
                "content": {
                    "tt": "{%nickname%}评论了你的博文{%url%}{%title%}",
                    "pd": "博客",
                    "nickname": "chiench",
                    "id": "78076912",
                    "title": "黑群晖(XPEnology)无法启动&重建系统并保留数据经验总结",
                    "url": "https://blog.csdn.net/littlebrain4solving/article/details/78076912#comments",
                    "tc": "",
                    "username": "chiench"
                },
                "username": "littlebrain4solving",
                "status": 1
            }
        ]
    },
    "status": true
}
```

### Comments
GET https://blog.csdn.net/{username}/phoenix/comment/list/78076912?page=1&size=15&tree_type=1

Response
```
{
    "result": 1,
    "callback": null,
    "data": {
        "count": 2,
        "page_count": 1,
        "floor_count": 2,
        "list": [
            {
                "info": {
                    "CommentId": "9699342",
                    "ArticleId": "78076912",
                    "BlogId": "4866377",
                    "ParentId": "0",
                    "PostTime": "2019-05-05 22:59:50",
                    "Content": "感謝分享",
                    "UserName": "chiench",
                    "Status": "0",
                    "IP": "114.34.159.57",
                    "IsBoleComment": "0",
                    "PKId": "0",
                    "Digg": "0",
                    "Bury": "0",
                    "SubjectType": "-1",
                    "WeixinArticleId": "0",
                    "digg_arr": [],
                    "Avatar": "https://profile.csdnimg.cn/B/5/7/3_chiench",
                    "NickName": "chiench",
                    "date_format": "5天前"
                }
            }
        ]
    },
    "vote": 0,
    "content": "success"
}
```

### Clear the unread message
POST https://msg.csdn.net/v1/web/message/read
```
{
	"id": 518702,
	"type": 0
}
```

### Add Comment
POST https://blog.csdn.net/littlebrain4solving/phoenix/comment/submit?id=78076912

Form-Data
* content - 内容 Required
* replyId - 回复评论 Optional

### Delete Comment
POST https://blog.csdn.net/littlebrain4solving/phoenix/comment/delete

Form-Data
* commentId - 评论编号 Required
* filename - 当前文章编号 Required
