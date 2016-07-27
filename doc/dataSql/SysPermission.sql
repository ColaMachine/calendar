/*
-- Query: SELECT * FROM colamachine.sys_permission
LIMIT 0, 1000

-- Date: 2016-07-27 11:23
*/
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (39,0,'用户管理','Res_SysAuth',1,1,'1');
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (40,39,'用户','Res_SysUser',1,1,'1');
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (41,39,'角色','Res_SysRole',2,1,'1');
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (42,39,'资源','Res_SysResource',1,1,'1');
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (43,39,'用户角色关联','Res_SysUserRole',4,1,'1');
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (44,39,'角色资源','Res_SysRoleResource',5,1,'1');
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (45,39,'用户资源关联','Res_SysUserResource',6,1,'1');
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (51,0,'海绵城市','Res_haimian',1,1,'1');
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (52,51,'新闻资讯','Res_Artical',1,1,'1');
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (53,51,'新闻资讯审核','Res_ArticalAuth',1,1,'1');
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (54,51,'专家智库','Res_expert',1,1,'1');
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (55,51,'合作伙伴','partner',2,1,'合作伙伴维护');
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (56,51,'专家介绍','ExpertDetail',1,1,NULL);
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (57,51,'专家文献','ExpertArtical',1,1,NULL);
INSERT INTO `sys_permission` (`id`,`pid`,`name`,`code`,`order`,`status`,`remark`) VALUES (58,51,'合作伙伴介绍','PartnerDetail',1,1,NULL);