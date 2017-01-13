CREATE TABLE `template` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(40) NOT NULL COMMENT '模板名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '描述',
  `face` varchar(100) DEFAULT 'static/img/timg.jpeg' COMMENT '图片',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='模板';
