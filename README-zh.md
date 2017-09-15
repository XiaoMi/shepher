![LOGO](../raw/master/Docs/images/logo_full_color_h100.png)

Shepher 是一款 ZooKeeper 的管理工具。在小米公司，我们用它作为配置管理中心。[Readme in English](README.md)

## 特性
- ZooKeeper 节点的可视化操作
- ZooKeeper 节点的快照管理
- ZooKeeper 节点修改的 Diff 和 Review 功能
- ZooKeeper 节点操作邮件通知
- 集成 CAS 和 LDAP 登录
- 权限管理，参照 [权限管理说明](Docs/Authority-zh.md)

## 同类产品功能对比

产品 | 简介 | 节点的可视化操作 | 快照管理 | 节点修改的 Diff 和 Review 功能 | 节点操作邮件通知 | CAS 和 LDAP 登录 | 权限管理 | 级联删除 | 系统状态监控
---|---|---|---|---|---|---|---|---|---
Shepher | ZooKeeper 管理 | √ | √ | √ | √ | √ | √ |   |  
TaoKeeper | ZooKeeper 集群监控与报表 |   |   |   |   |   |   |   | √
Zkdash | ZooKeeper 管理 | √ | √ |   |   |   |   | √ |  
Disconf | ZooKeeper 管理 | √ | √ |   | √ |   | √ | √ | √
XDiamond | 配置中心 | √ |   |   |   | √ | √ |   | √

## 系统截图
- 首页
![Home](../raw/master/Docs/images/home.png)

- 节点查看
![Node view](../raw/master/Docs/images/node-view.png)

## 安装

### 环境要求
- JDK 1.8
- Maven 3.2 +
- MySQL 5.6

### 基本配置

- 修改 `db/init.sql` 中的 `INSERT INTO user VALUES (1,'youradmin',now());` ，将 `youradmin` 改为你的管理员用户名
- 参照 [参数说明](Docs/Parameter-zh.md)，设置 `shepher-web/src/main/resources` 目录下的参数配置。如果使用 CAS 登录，则需要修改 CAS 相关的配置，并且将 `server.login.type` 设置为 `CAS`；如果使用 LDAP 登录，则需要修改 LDAP 相关的配置，并且将 `server.login.type` 设置为 `LDAP`

### 开发环境部署

开发环境部署包括本地编译部署和 Docker 部署两种方式，用户可以根据使用习惯选择一种部署方式。

#### 编译
1. 参照 [参数说明](Docs/Parameter-zh.md) 修改 `conf/application-default.properties` 的参数配置
2. 本地编译

    ```sh
    $ mvn clean package 
    $ cd shepher-packaging/target/shepher-packaging-{version}-bin
    $ ls
    bin/(all the shell scripts)
    conf/(configuration files)
    db/init.sql
    lib(required jar files)
    Dockerfile
    docker-compose.yml
    CHANGES.txt
    NOTICE.txt
    README.md
    README-zh.md
    VERSION

    ```
    这里， {version} 是指当前 Shepher 的版本。

#### 本地部署

1. 将 `db/init.sql` 导入到 MySQL
2. 进入到安装目录 

    ```sh
    $ cd shepher-packaging/target/shepher-packaging-{version}-bin
    ```
3. 运行脚本，启动服务 Shepher

    ```sh
    $ sh bin/run.sh start 
    ```
4. 在浏览器中访问 `http://localhost:8089` 或自定义的 `server.url` （参照 [参数说明](Docs/Parameter-zh.md)）


#### Docker 部署

使用 Docker 部署则自动集成 MySQL 和 ZooKeeper，并且自动将 `db/init.sql` 导入MySQL中，不需要自行安装。

1. 安装 Docker，以 Ubuntu 系统为例，安装 [docker engine](https://docs.docker.com/engine/installation/#installation) 和 [docker-compose](https://docs.docker.com/compose/install/)
2. 进入到安装目录

    ```sh
    $ cd shepher-packaging/target/shepher-packaging-{version}-bin
    ```
3. 运行脚本，启动服务 Shepher, 等待 Docker 中的各个容器启动完成

    ```sh
    $ sh bin/docker-run.sh start
    ```
4. 在浏览器中访问 `http://localhost:8089` 或自定义的 `server.url` （参照 [参数说明](Docs/Parameter-zh.md)）

### 生产环境部署

生产环境部署的步骤跟本地编译部署类似，主要注意对数据源、CAS/LDAP 以及域名的设置。另外，由于各个公司内部的邮件服务较为封闭，在线上使用中需要自己实现 `CustomMailSender` 类，并在 `shepher-web/src/main/resources/application.properties` 中设置 `mail.sender=customMailSender`，以便 Shepher 服务可以正常使用邮件服务。

1. 将 `db/init.sql` 导入到 MySQL
2. 创建 `conf/application-online.properties` 文件，参照 [参数说明](Docs/Parameter-zh.md) 添加和修改配置
3. 在 Shepher 根目录下运行命令

    ```sh
    $ mvn clean package
    ```
4. 将安装目录 shepher-packaging/target/shepher-packaging-{version}-bin 拷贝到线上,然后进入到该目录

    ```sh
    $ cd shepher-packaging/target/shepher-packaging-{version}-bin
    ```
5. 执行下面命令启动 Shepher

    ```sh
    $ sh bin/run.sh -c conf/application.properties,conf/application-online.properties start 
    ```
6. 在浏览器中访问 `http://localhost:8089` 或自定义的 `server.url` （参照 [参数说明](Docs/Parameter-zh.md)）

## 贡献者

**Long Xie** ([@xielong](https://github.com/xielong)),
**Jinliang Ou** ([@oujinliang](https://github.com/oujinliang)),
**Chuanyu Ban** ([@banchuanyu](https://github.com/banchuanyu)),
**Chuyang Wei** ([@brianway](https://github.com/brianway)),
**Peng Zhang** ([@monsters-peng](https://github.com/monsters-peng)),
**Yaoli Liu** ([@iloayuil](https://github.com/iloayuil))
