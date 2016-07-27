/*
-- Query: SELECT * FROM colamachine.sys_menu
LIMIT 0, 1000

-- Date: 2016-07-27 11:24
*/
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (39,0,'用户管理','Res_SysAuth','Res_SysAuth',NULL,1,1,'1');
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (40,39,'用户','Res_SysUser','Res_SysUser','/sysUser/list.htm',1,1,'1');
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (41,39,'角色','Res_SysRole','Res_SysRole','/sysRole/list.htm',2,1,'1');
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (42,39,'资源','Res_SysResource','Res_SysResource','/sysResource/list.htm',1,1,'1');
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (43,39,'用户角色关联','Res_SysUserRole','Res_SysUserRole','/sysUserRole/listMapper.htm',4,1,'1');
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (44,39,'角色资源','Res_SysRoleResource','Res_SysRoleResource','/sysRoleResource/listMapper.htm',5,1,'1');
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (45,39,'用户资源关联','Res_SysUserResource','Res_SysUserResource','/sysUserResource/listMapper.htm',6,1,'1');
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (51,0,'海绵城市','Res_haimian','Res_haimian',NULL,1,1,'1');
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (52,51,'新闻资讯','Res_Artical','Res_Artical','/artical/list.htm',1,1,'1');
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (53,51,'新闻资讯审核','Res_ArticalAuth','Res_ArticalAuth','/artical/listAudit.htm',1,1,'1');
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (54,51,'专家智库','Res_expert','Res_expert','/expert/list.htm',1,1,'1');
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (55,51,'合作伙伴','partner','partner','/partner/list.htm',2,1,'合作伙伴维护');
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (56,51,'专家介绍','ExpertDetail','ExpertDetail','/expertDetail/list.htm',1,1,NULL);
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (57,51,'专家文献','ExpertArtical','ExpertArtical','/expertArtical/list.htm',1,1,NULL);
INSERT INTO `sys_menu` (`id`,`pid`,`name`,`code`,`permission`,`url`,`order`,`status`,`remark`) VALUES (58,51,'合作伙伴介绍','PartnerDetail','PartnerDetail','/partnerDetail/list.htm',1,1,NULL);