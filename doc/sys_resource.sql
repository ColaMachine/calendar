/*
-- Query: SELECT * FROM colamachine.sys_resource
LIMIT 0, 1000

-- Date: 2016-05-11 14:06
*/
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (38,0,'日历','Res_Calendar','menu','/static/html/CalendarView.html',1,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (39,0,'用户管理','Res_SysAuth','menu',NULL,1,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (40,39,'用户','Res_SysUser','menu','/sysUser/list.htm',1,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (41,39,'角色','Res_SysRole','menu','/sysRole/list.htm',2,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (42,39,'资源','Res_SysResource','menu','/sysResource/list.htm',1,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (43,39,'用户角色关联','Res_SysUserRole','menu','/sysUserRole/listMapper.htm',4,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (44,39,'角色资源','Res_SysRoleResource','menu','/sysRoleResource/listMapper.htm',5,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (45,39,'用户资源关联','Res_SysUserResource','menu','/sysUserResource/listMapper.htm',6,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (46,49,'收藏','Res_CollectList','menu','/collect/listMapper.htm',5,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (47,49,'最热资源','Res_VideoHot','menu','/videoHot/list.htm',6,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (48,49,'最新资源','Res_videoNew','menu','/videoNew/list.htm',6,1,'123','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (49,0,'动物园管理','Res_ZooManager','menu',NULL,1,1,'0','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (50,49,'最热视频','Res_hotvideo','menu','/static/html/videoNew.html',4,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (51,0,'海绵城市','Res_haimian','menu',NULL,1,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (52,51,'新闻资讯','Res_Artical','menu','/artical/list.htm',1,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (53,51,'新闻资讯审核','Res_ArticalAuth','menu','/artical/listAudit.htm',1,1,'1','2016-05-10 23:43:56');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (54,51,'专家智库','Res_expert','menu','/expert/list.htm',1,1,'1','2016-05-09 10:38:09');
INSERT INTO `sys_resource` (`id`,`pid`,`name`,`code`,`type`,`url`,`order`,`status`,`remark`,`createtime`) VALUES (55,51,'合作伙伴','partner','menu','/partner/list.htm',2,1,'合作伙伴维护','2016-05-31 23:22:28');
