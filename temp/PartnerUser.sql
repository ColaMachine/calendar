CREATE TABLE `PartnerUser` (
 `id` int NOT NULL  AUTO_INCREMENT  COMMENT 'id',
 `userId` int NOT NULL   COMMENT '用户id',
 `partnerId` bigint NOT NULL   COMMENT '合作伙伴Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合作伙伴用户关系';
