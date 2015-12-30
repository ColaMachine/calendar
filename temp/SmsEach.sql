CREATE TABLE `SmsEach` (
 `id` int NOT NULL  AUTO_INCREMENT  COMMENT 'id',
 `batchId` int NOT NULL   COMMENT '批次号',
 `phone` int NOT NULL   COMMENT '手机号码',
 `sendtime` timestamp NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  COMMENT '发送时间',
 `status` int   DEFAULT 1  COMMENT '发送状态',
 `reason` varchar(200)    COMMENT '失败原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信发送情况';
