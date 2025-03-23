# 这是 ShitChat (史山传音) 的聊天服务器
ShitChat 是由 ShitTeam (史山团队) 开发的聊天程序, 其中聊天服务器是由 史山团队独自开发的([XinyuCraft](https://space.bilibili.com/3493280035637324) 贡献突出), 目前在 [本仓库](https://github.com/XinyuCraft/ChatServer) 开源, 支持的人不妨给我们点个 Star

## 开发进度
- [x] 世界聊天
- [x] 用户登录/注册
- [ ] 邮箱验证
- [ ] 好友功能
- [ ] 私聊

## 配置文件
ShitChat的配置文件为 `config.properties` 内容如下
``` Properties
##############################
#                            #
#         服务器配置          #
#                            #
##############################

# 服务器端口
serverPort=8888

##############################
#                            #
#         数据库配置          #
#                            #
##############################

# JDBC数据库驱动
JDBC_Driver = com.mysql.cj.jdbc.Driver
# 数据库URL
JDBC_URL = jdbc:mysql://localhost:3306/yourDatabase
# 数据库用户名
JDBC_User = root
# 数据库密码
JDBC_PassWord = yourpassword

##############################
#                            #
#          邮箱配置           #
#                            #
##############################

# SMTP服务器地址
mail.smtp.host=smtp.qq.com
# SMTP服务器端口
mail.smtp.port=465
# 是否开启SSL
mail.smtp.ssl=true
# 是否开启登录验证
mail.smtp.auth=true
# 发送人邮箱
mail.smtp.user=youremail@qq.com
# 发送人密码
mail.smtp.pass=yourpassword
mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
```

## 团队简介
Shit Team团队灵感来源于 [XinyuCraft](https://space.bilibili.com/3493280035637324) 与2023年创作的屎山迷宫("屎山"并无贬义的意义,只是出于xinyucraft的搞怪).现史山迷宫有2名老成员([XinyuCraft](https://space.bilibili.com/3493280035637324) 和[zzlcraft](https://github.com/zzlxsww))
