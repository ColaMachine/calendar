/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月12日
 * 文件说明: 
 */
package cola.machine.util.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory extends DefaultGenCodeFactory {
    private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);
    HashMap<String, ZTable> allTable;
    ZTable table ;
    private Map root ;
    public ServiceFactory(HashMap<String, ZTable> allTable, Map root) {
        this.allTable = allTable;
        this.root=root;
    }

    public void getService(String name) {
        logger.info("getService");
        this.table=allTable.get(name);

        if(table.getMapper()!=null && table.getMapper().getMapper().equals(table.getName())){
            StringBuffer sb =new StringBuffer();
            ZColum parentCol=GenCodeHelper.getColFromCols(table.getCols(),table.getMapper().getParentid());
            ZColum childCol=GenCodeHelper.getColFromCols(table.getCols(),table.getMapper().getChildid());
            GenCodeHelper.changeMySqlType2JavaType(parentCol.getType());
            root.put("parentCol",parentCol);
            root.put("childCol",childCol);

            root.put("parentType",GenCodeHelper.changeMySqlType2JavaType(parentCol.getType()));
            root.put("childType",GenCodeHelper.changeMySqlType2JavaType(childCol.getType()));

        }

    }

}
