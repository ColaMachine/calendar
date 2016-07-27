CREATE TABLE `sys_log` (
    `id` int NULL AUTO_INCREMENT COMMENT '编号',
    `path` varchar(100) NULL COMMENT '路径',
    `type` tinyint NULL COMMENT '日志类型',
    `code` int NULL COMMENT '代码',
    `param` varchar(200) NULL COMMENT '操作参数',
    `user` varchar(40) NULL COMMENT '用户',
    `msg` varchar(1000) NULL COMMENT '消息',
    `createTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `startTime` timestamp NULL DEFAULT NULL COMMENT '开始时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';


CREATE TABLE `sys_log` (
 `id` int   AUTO_INCREMENT  COMMENT '编号',
 `path` varchar(100)    COMMENT '路径',
 `type` tinyint    COMMENT '日志类型',
 `code` int    COMMENT '代码',
 `param` varchar(200)    COMMENT '操作参数',
 `user` varchar(40)    COMMENT '用户',
 `msg` varchar(1000)    COMMENT '消息',
 `createTime` timestamp    COMMENT '创建时间',
 `startTime` timestamp    COMMENT '开始时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

