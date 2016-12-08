package com.dozenx.web.system.portal.editorTempComp.dao;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import com.dozenx.web.system.portal.editorTempComp.bean.EditorTempComp;

public interface EditorTempCompMapper {
    
    int deleteByPrimaryKey(Long id);

    
    int insert(EditorTempComp record);

   
    int insertSelective(EditorTempComp record);

    
    EditorTempComp  selectByPrimaryKey(Long id);

    /**
     * 说明:根据主键修改所存在属性内容
     * @param editorTempComp
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKeySelective(EditorTempComp editorTempComp);

    /**
     * 说明:根据主键修改record完整内容
     * @param editorTempComp
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    int updateByPrimaryKey(EditorTempComp editorTempComp);

    /**
     * 说明:根据map查找bean结果集
     * @param editorTempComp
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<EditorTempComp> listByParams(Map editorTempComp);
    
    /**
     * 说明:根据bean查找bean结果集
     * @param editorTempComp
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    List<EditorTempComp> listByParams4Page(Map editorTempComp);
    
    /**
     * 说明:根据map查找map结果集
     * @param editorTempComp
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
   /* List<Map> selectMapByBean4Page(EditorTempComp editorTempComp);*/
    
   
    /**
     * 说明:根据map查找map结果集
     * @param editorTempComp
     * @return int 更新数量
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    /*List<EditorTempComp> selectBeanByMap4Page(HashMap map);
    
    int countByBean(EditorTempComp record);*/
    
    int countByParams(HashMap map);

    int countByOrParams(HashMap map);

      
    int deleteExtra(HashMap map);
}
