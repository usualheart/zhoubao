# zhoubao
一个用于研究生/博士/工作的每周工作汇报系统，springboot+vue+mysql。支持在线写周报、上传图片，支持pdf、markdown格式的周报文件。支持共享查看。

# 效果展示

## 登录界面

首页北京与bing.com图片同步,每天都有新鲜感。

![image-20200821113625685](img/image-20200821113625685.png)

## Markdown编辑器

支持在线使用Markdown语法写周报。

![登录及markdown展示](img/登录及markdown展示.gif)

## 表格式周报与图片上传

支持以在线表格的形式填写每周工作情况，同时还支持上传图片进行说明。

![在线html周报展示](img/在线html周报展示.gif)

# 部署方法

### 创建数据库

创建数据库，然后导入`zhoubao.sql`文件定义的结构。

### 创建阅览账号：用于分配给老师或领导

在user表中插入一条记录，user.type设置为1。为1表示是阅览账号，该类型账户目前可以阅览所有用户的周报。

### 修改项目配置文件

配置文件位于`src/main/java/resources`目录下，目前有4个配置文件

```sh
# 分别是默认、dev环境、prod环境下的数据库响应的配置文件
src\main\resources\application.properties
src\main\resources\application-dev.properties
src\main\resources\applicationbl-prod.properties
# 里边包含各环境周报文件存储位置的配置
src\main\resources\application.yml
```

#### 数据库配置

`application.properties`需要修改的部分

```properties
#需要将YourDatabaseName替换为导入了zhoubao.sql结构的数据库名
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/YourDatabaseName?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT
spring.datasource.username=username
spring.datasource.password=password
```

#### 周报文件存储位置

需要设置好周报文件存储位置，用于存储周报文件、周报图片等。

`application.yml`文件示例：

需要根据具体存储的位置进行设置，spring.resources.static-locations和zhoubao.location的属性需要保持一致。

```yaml
#yml可以把所有Profile的配置属性都放在一个application.yml文件中 通过---进行分割
#这里没有指定spring.profiles因此这里的属性对全部prifile都生效 
spring:
#  mvc:
#    static-path-pattern: /resources/**
  resources:
    static-locations: file:C:/Users/xyz/Documents/zhoubaoPublic/,  classpath:/static/
zhoubao:
  location: C:/Users/xyz/Documents/zhoubaoPublic/
server:
  port: 3000
    
---
# 下边的部分对设置的spring环境属性生效
#dev 环境配置
spring:
  profiles: dev
  resources:
    static-locations: file:C:/Users/xyz/Documents/zhoubaoPublic/, classpath:/static
zhoubao:
  location: C:/Users/xyz/Documents/zhoubaoPublic/
server:
  port: 3000
---
# prod 环境配置
spring:
  profiles: prod
  resources:
    static-locations: file:/home/user/Documents/zhoubaoPublic/, classpath:/static/
    
zhoubao:
  location: /home/user/Documents/zhoubaoPublic/
server:
  port: 3000
```

## 启动项目
