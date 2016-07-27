insert into  sys_permission
(`id`,`pid`,`name`,`code`,`order`,`status`,`remark` )
select `id`,`pid`,`name`,`code`,`order`,`status`,`remark`
from sys_resource;

insert into  sys_menu
(`id`,`pid`,`name`,`code`,`order`,`status`,`remark` ,`url`,`permission`)
select `id`,`pid`,`name`,`code`,`order`,`status`,`remark`,`url` ,`code`
from sys_resource;
