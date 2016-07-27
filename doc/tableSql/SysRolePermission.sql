CREATE TABLE `sys_role_permission` (
    `id` bigint(15) NULL AUTO_INCREMENT COMMENT '主键',
    `rid` bigint(15) NOT NULL COMMENT '角色id',
    `pid` bigint(15) NOT NULL COMMENT '权限id',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源关系';


CREATE TABLE `sys_role_permission` (
 `id` bigint(15)   AUTO_INCREMENT  COMMENT '主键',
 `rid` bigint(15) NOT NULL   COMMENT '角色id',
 `pid` bigint(15) NOT NULL   COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源关系';

