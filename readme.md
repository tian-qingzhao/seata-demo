### Seata的demo

#### 1.首先搭建 `Nacos` 服务端以及 `Seata` 服务端。
#### 2.在script目录下对应Seata-Server的数据库以及每个微服务对应的数据库。
#### 3. 在 order-service微服务下调用创建订单接口，业务层使用 `@GlobalTransactional` 注解即可。