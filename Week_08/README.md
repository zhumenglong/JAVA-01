# 第15课 超越分库分表-数据库拆分与分库分表

## 作业

1、(选做)分析前面作业设计的表，是否可以做垂直拆分。
2、(必做)设计对前面的订单表数据进行水平分库分表，拆分2个库，每个库16张表。 并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。
3、(选做)模拟1000万的订单单表数据，迁移到上面作业2的分库分表中。
4、(选做)重新搭建一套4个库各64个表的分库分表，将作业2中的数据迁移到新分库 。

### 作业 2 设计对前面的订单表数据进行水平分库分表，拆分2个库，每个库16张表。 并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github
[sql](./geektime-work15/sql)
[测试案例](./geektime-work15/src/test/java/DataSourceTest.java)

# 第15课 超越分库分表-数据库拆分与分库分表

## 超越分库分表-分布式事务

1、(选做)列举常见的分布式事务，简单分析其使用场景和优缺点。

2、(必做)基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布 式事务应用 demo(二选一)，提交到 Github。

3、(选做)基于 ShardingSphere narayana XA 实现一个简单的分布式事务 demo。

4、(选做)基于 seata 框架实现 TCC 或 AT 模式的分布式事务 demo。

5、(选做☆)设计实现一个简单的 XA 分布式事务框架 demo，只需要能管理和调用2 个 MySQL 的本地事务即可，不需要考虑全局事务的持久化和恢复、高可用等。

6、(选做☆)设计实现一个 TCC 分布式事务框架的简单 Demo，需要实现事务管理器， 不需要实现全局事务的持久化和恢复、高可用等。

7、(选做☆)设计实现一个 AT 分布式事务框架的简单 Demo，仅需要支持根据主键 id 进行的单个删改操作的 SQL 或插入操作的事务。

### 作业2 基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布 式事务应用 demo(二选一)，提交到 Github。

[hmily TCC](./geektime-work16/shardingsphere-XA/src/test/java/DataSourceTest.java)

[ShardingSphere XA](./geektime-work16/hmily-tcc/src/main/java/pers/peixinyi/work/tcc/service/impl/HelloServiceImpl.java)