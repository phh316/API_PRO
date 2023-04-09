package com.domain.entity;

import com.domain.api.annotation.APIAsHeader;
import com.domain.api.annotation.APIAsInPut;
import com.domain.api.annotation.XmlAsInPut;
import com.domain.api.annotation.XmlAsOutPut;
import com.domain.api.core.APIConstant;
import com.domain.api.http.HttpAPIObject;
import com.domain.api.http.RestAPIObject;


/**
 * Created by pei hao on 2021/9/1.
 */
public class A018253683 extends HttpAPIObject {

    @APIAsHeader(name = "head")
    public String head ;

    @APIAsInPut(name = "id",desc = "用户id",fromFie = false)
    public String id;

    @APIAsInPut(name = "name",desc = "用户名",fromFie = false)
    public String name;

    @APIAsInPut(name = "age",desc = "用户年龄",fromFie = false)
    public String age;

    //xmllist没有下标
    @XmlAsOutPut(name = "transnum",desc = "交易码",xpath = "/channel/item[2]/transnum" )
    public int transnum;

    @Override
    public String getReturnType() {
        return APIConstant.API_RESPONSE_XML;
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
    public String getInputType() {
        return APIConstant.API_Input_Type_XML;
    }

    @Override
    public String getSendUri() {
        return "/api/postxml";
    }
}
