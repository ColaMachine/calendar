CREATE TABLE `sys_permission` (
    `id` bigint(10) NULL AUTO_INCREMENT COMMENT '编号',
    `pid` bigint(10) NULL COMMENT '父主键',
    `name` VARCHAR(20) NOT NULL COMMENT '权限名称',
    `code` VARCHAR(20) NOT NULL COMMENT '权限代码',
    `order` int(11) NULL COMMENT '排序id',
    `status` int(1) NOT NULL COMMENT '状态',
    `remark` varchar(20) NULL COMMENT '备注',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限配置';


CREATE TABLE `sys_permission` (
 `id` bigint(10)   AUTO_INCREMENT  COMMENT '编号',
 `pid` bigint(10)    COMMENT '父主键',
 `name` VARCHAR(20) NOT NULL   COMMENT '权限名称',
 `code` VARCHAR(20) NOT NULL   COMMENT '权限代码',
 `order` int(11)    COMMENT '排序id',
 `status` int(1) NOT NULL   COMMENT '状态',
 `remark` varchar(20)    COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限配置';

