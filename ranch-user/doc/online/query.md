# 获取在线用户信息集

请求
- Service Key - ranch.user.online.query
- URI - /user/online/query

参数
- user 用户ID。
- uid 认证ID，仅当user为空时有效。
- ip IP地址。
- pageSize 每页显示记录数，默认20。
- pageNum 当前显示页数。

返回值
```json
{
  "count": "总记录数",
  "size": "每页显示记录数",
  "number": "当前显示页数",
  "list": [
    {
      "id": "ID值",
      "user": "用户",
      "ip": "IP地址",
      "sid": "Session ID",
      "signIn": "登入时间",
      "lastVisit": "最后访问时间"
    }
  ]
}
```

> 后台管理接口，需验证[请求参数签名](https://github.com/heisedebaise/tephra/blob/master/tephra-ctrl/doc/sign.md)。