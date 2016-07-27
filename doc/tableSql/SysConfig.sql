CREATE TABLE `SysConfig` (
    `id` int NULL AUTO_INCREMENT COMMENT '编号',
    `key` varchar(50) NULL COMMENT '名称',
    `valuate` varchar(50) NOT NULL DEFAULT 0 COMMENT '对应值',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表';


CREATE TABLE `SysConfig` (
 `id` int   AUTO_INCREMENT  COMMENT '编号',
 `key` varchar(50)    COMMENT '名称',
 `valuate` varchar(50) NOT NULL  DEFAULT 0  COMMENT '对应值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表';

