# 参数说明

参数 | 含义 | 限定可选值 | 备注
---|---|---|---
mail.hostname | generalMailSender 使用的邮件服务器 |
mail.port | generalMailSender 使用的邮件服务器端口 |
mail.username | generalMailSender 用户名 |
mail.password | generalMailSender 认证码 |
mail.from | generalMailSender 发件人 |
mail.fromname | generalMailSender 发件人显示名 |
**mail.sender** | Shepher 系统使用的邮件发送方式 | generalMailSender/customMailSender | customMailSender 是自定义邮件类，参考CustomMailSender
**cas.server.url.prefix** | CAS 认证服务器地址 |
cas.login.url | CAS 认证服务器登录入口 |
cas.logout.url | CAS 认证服务器登出入口 |
**ldap.url** | LDAP 认证服务地址 |
ldap.password | LDAP 认证密码 |
ldap.dn | LDAP 认证 DN |
**server.url** | Shepher 服务地址 |
**server.port** | Shepher 服务端口 |
**mail.mailToSuffix** | 邮件接收人地址后缀 |
mail.mailAddressEndSeparator | 多邮件接收人分隔符 |
**server.login.type** | Shepher 登录类型 | CAS/LDAP | 目前仅支持 CAS 和 LDAP 两种登录方式