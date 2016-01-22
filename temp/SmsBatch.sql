CREATE TABLE `SmsBatch` (
 `id` int   AUTO_INCREMENT  COMMENT 'id',
 `total` int NOT NULL  DEFAULT 0  COMMENT '总发送数据',
 `succ` int NOT NULL  DEFAULT 0  COMMENT '成功数量',
 `fail` int NOT NULL  DEFAULT 0  COMMENT '失败数量',
 `status` int NOT NULL  DEFAULT 0  COMMENT '状态',
 `phone` text NOT NULL   COMMENT '手机号码',
 `content` varchar(500) NOT NULL   COMMENT '短信内容',
 `sendTime` timestamp    COMMENT '定时发送',
 `excuteTime` timestamp    COMMENT '执行时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='批量发送短信';
