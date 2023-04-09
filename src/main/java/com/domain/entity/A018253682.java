package com.domain.entity;

import com.domain.api.annotation.APIAsHeader;
import com.domain.api.annotation.APIAsInPut;
import com.domain.api.annotation.JsonAsOutPut;
import com.domain.api.core.APIConstant;
import com.domain.api.http.HttpAPIObject;
import com.domain.api.http.RestAPIObject;


/**
 * Created by pei hao on 2021/9/1.
 */
public class A018253682 extends RestAPIObject{

    private String url;

    public A018253682(String url){
        this.url = url;
    }

    @APIAsHeader(name = "test")
    public String test = "9898787787";

    @APIAsInPut(name = "id",desc = "用户id",fromFie = false)
    public String id;

    @APIAsInPut(name = "name",desc = "用户名",fromFie = false)
    public String name;

    @APIAsInPut(name = "age",desc = "用户年龄",fromFie = false)
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
        return APIConstant.API_METHOD_POST;
    }

    @Override
    public String getSendUri() {
        return url;
    }


}
