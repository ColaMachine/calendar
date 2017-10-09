package com.dozenx.web.system.portal.template.dao;

import com.dozenx.web.system.portal.template.bean.Template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TemplateMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(Template record);

   
    int insertSelective(Template  record);

    
    Template  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param template
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(Template template);

    /**
     * 说明:根据主键修改record完整内容
     * @param template
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(Template template);

    /**
     * 说明:根据map查找bean结果集
     * @param template
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Template> listByParams(Map template);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param template
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<Template> listByParams4Page(Map template);
    
    /**
     * 说明:根据map查找map结果集
     * @param template
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(Template template);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param template
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<Template> selectBeanByMap4Page(HashMap map);
    
    int countByBean(Template record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
}
