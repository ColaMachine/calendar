package cola.machine.dao;
 <#assign abc="${table.name[0]?lower_case}${table.name[1..]}">
<#assign Abc="${table.name[0]?upper_case}${table.name[1..]}">
import java.util.HashMap;
import java.util.List;

import cola.machine.bean.${Abc};

public interface ${Abc}Mapper {
    
    int deleteByPrimaryKey(<@javaType>${table.pk.type}</@javaType> ${table.pk.name});

    
    int insert(${Abc} record);

   
    int insertSelective(${Abc}  record);

    
    ${Abc}  selectByPrimaryKey(String id);

   
     
    int updateByPrimaryKeySelective(${Abc} record);

    
    int updateByPrimaryKey(${Abc} record);

    
    List<${Abc}> selectByMap(HashMap map);
    
      List<${Abc}>  selectByBean(${Abc}  record);
      
      
}