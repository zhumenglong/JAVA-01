-- 用户表
create table `mall_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(50) character set utf8mb4 collate utf8mb4_bin not null comment '密码',
  `nickname` varchar(10) character set utf8mb4 collate utf8mb4_bin default null comment '昵称',
  `mobile` varchar(13) character set utf8mb4 collate utf8mb4_bin default null comment '手机号码',
  `last_login_time` timestamp null default null comment '最后登陆时间',
  `create_time` timestamp not null comment '创建时间',
  `last_update_time` timestamp not null comment '最后更新时间',
  `delete_tag` bit(1) default b'0' comment '删除标记',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_bin;

create table `mall_receiver_info`(
   `id` INT NOT NULL AUTO_INCREMENT '收件人id',
   `user_id` INT NOT NULL comment '用户id',
   `receiver_name` varchar(10) NOT NULL comment '收件人姓名',
   `receiver_address` VARCHAR(45) NOT NULL comment '收件人详细地址',
   `default_flag` INT NOT NULL comment '是否是默认收货人：1-是 2-否',
 PRIMARY KEY (`id`));

-- 商品表
CREATE TABLE `mall_goods` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `price` DECIMAL(18,2) NOT NULL comment '单价',
  `name` VARCHAR(45) NOT NULL comment '商品名称',
  `status` INT NOT NULL comment '商品状态：1-正常 2-删除',
PRIMARY KEY (`id`));

CREATE TABLE `mall_goods_sku` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `goods_id` INT NOT NULL comment '商品id',
  `price` DECIMAL(18,2) NOT NULL comment '单价',
  `pic_url` varchar(100) NOT NULL comment '商品图片',
  `stock` INT NULL comment '库存',
  `sold_num` INT NOT NULL comment '总销量',
PRIMARY KEY (`id`));

CREATE TABLE `mall_goods_sku_property` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `goods_sku_id` INT NOT NULL comment '商品SKU-ID',
  `property_name` DECIMAL(18,2) NOT NULL comment '属性名：颜色|大小|容量',
  `property_value` INT NULL comment '属性值',
PRIMARY KEY (`id`));

CREATE TABLE `mall_goods_snapshot` (
  `id` INT NOT NULL AUTO_INCREMENT comment '商品快照表ID',
  `goods_id` INT NOT NULL comment '商品id',
  `goods_sku_id` INT NOT NULL comment '商品SKU-ID',
  `name` VARCHAR(45) NOT NULL comment '商品名称',
  `price` DECIMAL(18,2) NOT NULL comment '单价',
  `pic_url` varchar(100) NOT NULL comment '商品图片',
  `property_name` DECIMAL(18,2) NOT NULL comment '属性名：颜色|大小|容量',
  `property_value` INT NULL comment '属性值',
  `create_time` timestamp not null comment '创建时间',
PRIMARY KEY (`id`));

-- 订单表
CREATE TABLE `mall_order` (
  `id` INT NOT NULL,
  `user_id` INT NULL comment '用户id',
  `receiver_info_id` INT NULL comment '收货人id',
  `total_price` DECIMAL(18,2) NULL comment '总金额',
  `total_discount` DECIMAL(18,2) NULL comment '总折扣',
  `status` VARCHAR(1) NULL comment '订单状态(1-待付款，2-付款中，3-已付款|待发货，4-待收货|已发货，5-交易成功|待评价，6-交易已关闭)',
  `close_status` VARCHAR(1) NULL comment '关闭状态（1-超时未支付， 2-退款关闭 ，3-买家取消 ，4-已通过货到付款交易 可能还有其他关闭原因）',
  `create_time` timestamp not null comment '创建时间',
  `update_time` timestamp not null comment '更新时间',
  PRIMARY KEY (`id`));

-- 订单明细
CREATE TABLE `mall_order_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `goods_snapshot_id` INT NULL,
  `quantity` INT NULL,
  `discount` DECIMAL(18,2) NULL comment '折扣',
  `total_price` DECIMAL(18,2) NULL comment '金额',
  PRIMARY KEY (`id`));
