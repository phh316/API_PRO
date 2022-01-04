package com.domain.entity;

import com.domain.api.annotation.APIAsHeader;
import com.domain.api.annotation.APIAsQueryString;
import com.domain.api.annotation.JsonAsOutPut;
import com.domain.api.core.APIConstant;
import com.domain.api.http.RestAPIObject;


/**
 * Created by pei hao on 2021/9/1.
 */
public class A01825368 extends RestAPIObject {

    @APIAsHeader(name = "cookie")
    public String cookie;

    @APIAsQueryString(name = "name")
    public String name;

    @JsonAsOutPut(jsonPath = "$.data.persons[1].name")
    public String name1;

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
        return "/db8122608751bfc0a73df8b9e3da74ad/test/api/user";
    }

}
