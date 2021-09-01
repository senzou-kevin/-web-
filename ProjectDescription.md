

# 旅游网项目
- [旅游网项目](#旅游网项目)
  - [简介:](#简介)
  - [用户角色](#用户角色)
  - [用户故事](#用户故事)
  - [系统架构图](#系统架构图)
  - [技术使用](#技术使用)
  - [E-R 图](#e-r-图)
  - [线框图](#线框图)
## 简介:

为顾客提供旅游路线，景区门票，酒店等服务。用户可以收藏自己喜欢的旅游线路，景区门票，酒店等。当用户登录后可以通过我的收藏进行查看。用户可以根据收藏排行榜查看哪些线路，酒店受欢迎。当用户查看旅游线路详情时，可以查看该路线被收藏次数。

## 用户角色

张三      22岁 在校大学生

1.毕业旅行，希望能找到有关国内游的旅游路线，并希望能查看酒店以及门票信息。

2.希望能将有意向的路线，酒店以及门票进行收藏。

李四    38岁 已婚育有一子

1.希望能搜索到有关亲子旅游线路。

王五，30岁 旅游爱好者

1.希望能提供一些热门推荐

## 用户故事

作为学生，我希望能看到国内的旅游路线。

作为学生，我希望能查看每条路线的详情。

作为学生，我希望能收藏路线。

作为学生，我希望能取消收藏路线。

作为学生，我希望能查看到我的收藏列表

作为家长，我希望能搜索到有关亲子的旅游路线。

作为旅游爱好者，我希望能看到一些热门推荐旅游路线。

## 系统架构图

![系统架构图.jpg](https://i.loli.net/2021/09/02/RUq5z7ICd9lEvf4.png)



**客户端:**采用浏览器，并使用外部的Bootstrap和JQuery

**中间件:**使用Tomcat服务器和前端交互。使用MySqlConnector,Spring提供的JdbcTempplate和阿里巴巴提供的Druid数据库连接池和数据库交互。采用web三层架构模式将web层，业务逻辑层和数据库访问层实现分离。

**数据库:**采用MySQL

## 技术使用

前端**:html,css,javascript,jquery,bootstrap,ajax**

Web: **servlet, html,BeanUtils, Jackson**

Service: **Redis, Jedis,** 

Dao: **MySQL 数据库,MySQL connector, Druid, Spring Jdbc Template,** 

Web服务器:**Tomcat 8**

Jar 包管理工具: **maven**

## E-R 图

![E-R图1.jpg](https://i.loli.net/2021/09/02/gZhuIJDA48e1Bbp.png)

用户和线路的关系是多对多的关系。一个用户可以有多个线路。一个线路可以被多个用户拥有。

分类和线路是1对多关系。1个分类可以有多个线路，多个线路属于一个分类。

商家和旅游路线时一对多的关系。一个商家可以提供多条线路。

线路和商品图片是一对多关系，一个线路可以有多个商品图片。

![E-R图2.jpg](https://i.loli.net/2021/09/02/JYknfA4V2zKibRX.png)



由于用户和线路是多对多关系，因此可以拆分为一对多和多对一关系。一个用户可以有多个收藏，多个收藏可以属于一个线路。

**可以将E-R图转换为表格形式:**

| tab_user  |                                |
| --------- | ------------------------------ |
| Field     | Type                           |
| uid       | Int,auto_increment,primary key |
| username  | varchar(100),unique            |
| password  | varchar(32)                    |
| name      | Varhcar(100)                   |
| birthday  | Date                           |
| sex       | Char(1)                        |
| telephone | Varchar(100)                   |
| email     | varchar(100)                   |

| tab_favorite |                             |
| ------------ | --------------------------- |
| Field        | Type                        |
| rid          | int,primary key,foreign key |
| date         | Date                        |
| uid          | priamry key,foreign key     |



| tab_route      |                                |
| -------------- | ------------------------------ |
| Field          | Type                           |
| rid            | Int,auto_increment,primary key |
| rname          | varchar(500)                   |
| price          | Double                         |
| routeIntroduce | varchar(1000)                  |
| rflag          | char(1)                        |
| rdate          | varchar(19)                    |
| isThemeTour    | char(1)                        |
| count          | Int                            |
| cid            | Int,foreign key                |
| rimage         | varchar(200)                   |
| sid            | Int,foreign key                |



| tab_category |                                |
| ------------ | ------------------------------ |
| Field        | Type                           |
| cid          | Int,auto_increment,primary key |
| cname        | varchar(100),unique            |



| tab_seller |                                |
| ---------- | ------------------------------ |
| Field      | Type                           |
| sid        | Int,auto_increment,primary key |
| sname      | varchar(200)                   |
| consphone  | varchar(20)                    |
| address    | varchar(200)                   |



| tab_rout_img |                                  |
| ------------ | -------------------------------- |
| Field        | Type                             |
| rgid         | Int, auto_increment, primary key |
| rid          | Int, foreign key                 |
| bigPic       | Varchar(200)                     |
| smallPic     | varchar(200)                     |

## 线框图

**首页**

![首页.png](https://i.loli.net/2021/09/02/fpFvGlt2q8D16go.png)





**登录**

![登录.png](https://i.loli.net/2021/09/02/twQIDzZKPmJceNd.png)





**注册**

![注册.png](https://i.loli.net/2021/09/02/zQEJWslmZThkUxN.png)





**线路分页展示**

![线路分页展示.png](https://i.loli.net/2021/09/02/crekMIKvpSgDnyz.png)



**线路详情**

![线路详情.png](https://i.loli.net/2021/09/02/NnVBDl8EWMYdX2c.png)





**收藏排行榜**

![收藏排行榜.png](https://i.loli.net/2021/09/02/wcd3mtaKTjuReVv.png)





**我的收藏**

![我的收藏.png](https://i.loli.net/2021/09/02/QiZ9517lxO4gdIj.png)



