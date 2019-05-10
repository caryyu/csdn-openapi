# csdn-openapi
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

### Comments
GET https://blog.csdn.net/{username}/phoenix/comment/list/78076912?page=1&size=15&tree_type=1

### Dis Message
POST https://msg.csdn.net/v1/web/message/read
```
{
	"id": 518702,
	"type": 0
}
```