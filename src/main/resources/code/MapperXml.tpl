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
 <insert id="insert" <#if table.pk.ai==true>useGeneratedKeys="true" keyProperty="id"</#if>   parameterType="cola.machine.bean.${Abc}" >

    insert into ${table.name} (  <include refid="Base_Column_List" />)
    values (
     <#list table.cols as col>
      <#if col_index==0><#else>,</#if>${r'#{'}${col.name},jdbcType=<@jdbcType>${col.type}</@jdbcType>}
    </#list>
    )
  </insert>
   <insert id="insertSelective" parameterType="cola.machine.bean.${Abc}" >
    insert into ${table.name}
    <trim prefix="(" suffix=")" suffixOverrides="," >
      <#list table.cols as col>
        <if test="${col.name} != null" >  
           ${col.name}, 
        </if>  
    </#list>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides="," >
       <#list table.cols as col>
        <if test="${col.name} != null" >  
          ${r'#{'}${col.name},jdbcType=<@jdbcType>${col.type}</@jdbcType>},
        </if>  
    </#list>
    </trim>
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="cola.machine.bean.${Abc}" >
    update ${table.name}
    <set >
     <#list table.cols as col>
        <if test="${col.name} != null" >  
             ${col.name}=${r'#{'}${col.name},jdbcType=<@jdbcType>${col.type}</@jdbcType>},
        </if>  
    </#list>
    </set>
    where id = ${r'#{'}${table.pk.name},jdbcType=<@jdbcType>${table.pk.type}</@jdbcType>}
  </update>
  <update id="updateByPrimaryKey" parameterType="cola.machine.bean.${Abc}" >
    update ${table.name}
    set 
    <#list table.cols as col>
        <#if col.pk!=true >
              ${col.name}=${r'#{'}${col.name},jdbcType=<@jdbcType>${col.type}</@jdbcType>}<#if col_index<table.cols?size-1>,</#if>  
        </#if>
    </#list>
where id = ${r'#{'}${table.pk.name},jdbcType=<@jdbcType>${table.pk.type}</@jdbcType>}
  </update>
  <select id="listByParams" parameterType="map" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${table.name} where 1=1
    <#list table.cols as col>
        <if test="${col.name} != null and ${col.name} != '' ">  
           and ${col.name} = ${r'#{'}${col.name}}
        </if>  
    </#list>
  </select>
   <select id="listByParams4Page" parameterType="map" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${table.name} where 1=1
    <#list table.cols as col>
        <if test="${col.name} != null and ${col.name} != '' ">  
           and ${col.name} = ${r'#{'}${col.name}}
        </if>  
    </#list>
  </select>
   <select id="countByParams" parameterType="map" resultType="java.lang.Integer">
    select 
    count(1) 
    from ${table.name} where 1=1
    <#list table.cols as col>
        <if test="${col.name} != null and ${col.name} != '' ">  
           and ${col.name} = ${r'#{'}${col.name}}
        </if>  
    </#list>
  </select>

</mapper>
