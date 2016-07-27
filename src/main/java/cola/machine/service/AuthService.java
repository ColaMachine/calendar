/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package cola.machine.service;

import java.util.*;

import javax.annotation.Resource;

import cola.machine.bean.*;
import cola.machine.dao.*;
import cola.machine.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import core.page.Page;
import core.action.ResultDTO;

@Service("authService")
public class AuthService extends BaseService {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthService.class);


    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysAuthMapper authMapper;

    /**
     * 根据用户id查询用户资源信息
     * @param userid
     * @return
     */
   /* public List<SysResource> listResourcesByUserid(Long userid){
       return  authMapper.selectResourceByUserId(userid);
    }
    public List<SysResource> listMenuResourcesByUserid(Long userid){
        return  authMapper.selectMenuResourceByUserId(userid);
    }*/
    public List<SysPermission> listPermissionByUserid(Long userid){
        return  authMapper.selectPermissionByUserId(userid);
    }

    /**
     * 根据userid 获取当前的权限菜单
     * @param userid
     * @return
     */
    public List<SysMenu> listMenusByUserid(Long userid){
        List<SysPermission> permissions = this.listPermissionByUserid(userid);
        HashSet<String > set =new HashSet<String>();
        for(SysPermission sysPermission:permissions){
            set.add(sysPermission.getCode());

        }
        List<SysMenu> menus = sysMenuService.listByParams(new HashMap());
        Object [] permissionAry =set.toArray();
        String[] permissionStrAry = new String[set.size()];

        Iterator it = set.iterator();
        int j =0;
        while(it.hasNext()){
            permissionStrAry[j]=(String)it.next();
            j++;
        }
        for(int i=menus.size()-1;i>=0;i--){
            SysMenu sysMenu = menus.get(i);
            String permissionStr = sysMenu.getPermission();

            if(StringUtil.isBlank(sysMenu.getUrl())){
                continue;
            }
            if(!PermissionUtil.hasPermission(sysMenu.getPermission(),permissionStrAry)){
                menus.remove(i);
            }

        }
        return menus;
    }
}
