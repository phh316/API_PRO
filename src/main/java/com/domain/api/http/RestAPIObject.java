package com.domain.api.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.domain.api.core.APIConstant;
import com.domain.api.core.APIRunnerFactory;
import com.domain.api.core.AbstractAPIBaseObject;
import com.domain.api.utils.GlobalSettings;
import com.domain.api.utils.Log;

/**
 * Created by pei hao on 2021/9/1.
 */
public abstract class RestAPIObject extends AbstractAPIBaseObject {

    public  abstract String getReturnType();
    public  abstract String getMethod();
    public  abstract String getSendUri();

    /**
     * 构造方法
     */
    public RestAPIObject() {
        super();
    }

    @Override
    public String run() {
        this.setRequestFileds();
        Log.info("获取到的uri:"+ this.getUrl());
        String response =  APIRunnerFactory.getFectory().getInstance(this.getType(),this.getMethod()).run(this);
        System.out.println(response);
        String result = JSON.toJSONString(JSONObject.parseObject(response), SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        Log.info("接口返回报文如下：\r\n"+ result);
        this.setResponseFields(response);
        return processResponse(response);
    }
    public String processResponse(String response){
        return response;
    }

    @Override
    public String getType() {
        return APIConstant.API_TYPE_HTTP;
    }

    @Override
    public String getUrl() {
        return GlobalSettings.getProperty("RestUrl")+getSendUri();
    }

    @Override
    public String getCharSet() {
        return APIConstant.API_CHARSET_UTF8;
    }



}
