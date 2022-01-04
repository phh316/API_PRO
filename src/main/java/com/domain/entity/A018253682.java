package com.domain.entity;

import com.domain.api.annotation.APIAsHeader;
import com.domain.api.annotation.APIAsQueryString;
import com.domain.api.core.APIConstant;
import com.domain.api.http.HttpObject;


/**
 * Created by pei hao on 2021/9/1.
 */
public class A018253682 extends HttpObject{

    @APIAsHeader(name = "cookie")
    public String cookie;

    @APIAsQueryString(name = "age")
    public String age;

    @APIAsQueryString(name = "disname")
    public String disname;

//    @JsonAsOutPut(name = "name",jsonPath = "$.data.person[0].name")
//    public String name;

    @Override
    public String getReturnType() {
        return APIConstant.API_RESPONSE_JSON;
    }

    @Override
    public String getCharSet() {
        return APIConstant.API_CHARSET_UTF8;
    }

    @Override
    public String getMethod() {
        return APIConstant.API_METHOD_GET;
    }

    @Override
    public String getSendUri() {
        return "/db8122608751bfc0a73df8b9e3da74ad/test/api/test";
    }

}
