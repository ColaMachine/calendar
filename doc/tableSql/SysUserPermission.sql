CREATE TABLE `sys_user_permission` (
    `id` bigint(10) NULL AUTO_INCREMENT COMMENT '主键',
    `uid` bigint(10) NOT NULL COMMENT '用户id',
    `pid` bigint(10) NOT NULL COMMENT '权限id',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户资源关系';


CREATE TABLE `sys_user_permission` (
 `id` bigint(10)   AUTO_INCREMENT  COMMENT '主键',
 `uid` bigint(10) NOT NULL   COMMENT '用户id',
 `pid` bigint(10) NOT NULL   COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户资源关系';

