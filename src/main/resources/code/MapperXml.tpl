<?xml version="1.0" encoding="UTF-8"?>
<#assign abc="${table.name[0]?lower_case}${table.name[1..]}">
<#assign Abc="${table.name[0]?upper_case}${table.name[1..]}">
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cola.machine.dao.${Abc}Mapper">
  <resultMap id="BaseResultMap" type="cola.machine.bean.${Abc}">
   <#list table.cols as col>
      <<#if col.pk==true>id<#else>result</#if> column="${col.name}" jdbcType="<@jdbcType>${col.type}</@jdbcType>" property="${col.name}" />
    </#list>
  </resultMap>
  <sql id="Base_Column_List">
    <#list table.cols as col><#if col_index==0>${col.name}<#else>,${col.name}</#if> </#list>
   
  </sql>
  <select id="selectByPrimaryKey" parameterType="java.lang.<@javaType>${table.pk.type}</@javaType>" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${table.name}
    where ${table.pk.name} =   ${r'#{'}${table.pk.name},jdbcType=<@jdbcType>${table.pk.type}</@jdbcType>}
  </select>
  <delete id="deleteByPrimaryKey" parameterType="java.lang.<@javaType>${table.pk.type}</@javaType>">
    delete from ${table.name}
    where ${table.pk.name} = ${r'#{'}${table.pk.name},jdbcType=<@jdbcType>${table.pk.type}</@jdbcType>}
  </delete>
 
  
  <select id="selectBeanByBean" parameterType="BaseResultMap" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${table.name} where 1=1
    <#list table.cols as col>
        <if test="${col.name} != null and ${col.name} != '' ">  
           and ${col.name} = ${r'#{'}${col.name}}
        </if>  
    </#list>
  </select>
</mapper>