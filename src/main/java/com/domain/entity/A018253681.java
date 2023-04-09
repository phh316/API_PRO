package com.domain.entity;

import com.domain.api.annotation.APIAsQueryString;
import com.domain.api.annotation.JsonAsOutPut;
import com.domain.api.core.APIConstant;
import com.domain.api.http.HttpAPIObject;


/**
 * Created by pei hao on 2021/9/1.
 */
public class A018253681 extends HttpAPIObject {


    @APIAsQueryString(name = "name")
    public String name;

    @APIAsQueryString(name = "age")
    public String age;

    @JsonAsOutPut(jsonPath = "$.data.person[0].name")
    public String rpname;

    @Override
    public String getReturnType() {
        return APIConstant.API_RESPONSE_JSON;
    }

    @Override
    public String getInputType() {
        return APIConstant.API_Input_Type_JSON;
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
        return "/api/get";
    }
}
