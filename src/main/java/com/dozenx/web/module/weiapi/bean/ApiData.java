package com.dozenx.web.module.weiapi.bean;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dozen.zhang on 2017/3/14.
 */
public class ApiData {
    String path;
    String httpType;
    String summary;

    String description;
    List<ApiParameter> parameters;
    List<String> tags;
    HashMap response;

}
