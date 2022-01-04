package com.domain.entity;

import com.domain.api.annotation.APIAsHeader;
import com.domain.api.annotation.APIAsInPut;
import com.domain.api.annotation.JsonAsOutPut;
import com.domain.api.core.APIConstant;
import com.domain.api.http.HttpObject;


/**
 * Created by pei hao on 2021/9/1.
 */
public class A018253681 extends HttpObject {

    @APIAsInPut
    public String para1;

    @APIAsInPut
    public String para2;

    @APIAsHeader
    public String header1;

    @APIAsHeader
    public String para3;

    @JsonAsOutPut(jsonPath = "$.data.persons[1].name")
    public String name;

    @Override
    public String getCharSet() {
        return APIConstant.API_CHARSET_UTF8;
    }

    @Override
    public String getReturnType() {
        return APIConstant.API_RESPONSE_JSON;
    }


    @Override
    public String getMethod() {
        return APIConstant.API_METHOD_POST;
    }

    @Override
    public String getSendUri() {
        return "mock/db8122608751bfc0a73df8b9e3da74ad/test/api/test";
    }
}
