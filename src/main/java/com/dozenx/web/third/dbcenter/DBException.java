package com.dozenx.web.third.dbcenter;

import com.dozenx.util.JsonUtil;

/**
 * Created by dozen.zhang on 2017/2/27.
 */
public class DBException extends  Exception{
    DBResultDTO result;
    public DBException(DBResultDTO dto){
        super(JsonUtil.toJsonString(dto));
        this.result = dto;
    }
}
