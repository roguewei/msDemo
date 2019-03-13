# Getting Started

### Guides
The following guides illustrates how to use certain features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

# 笔记
# mycat 是阿里巴巴旗下的分库分表工具

# 秒杀接口优化
# 思路： 减少数据库访问
# 1、系统初始化，把商品库存数量加载到redis
# 2、收到请求，redis预减库存，库存不足直接返回，否则进入3
# 3、请求入队，立即返回排队中
# 4、请求出队，生成订单，减少库存
# 5、客户端轮训，是否秒杀成功