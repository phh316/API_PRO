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
        String result = null;
        this.setRequestFileds();
        Log.info("获取到的url: \t" + this.getUrl());
        String response = APIRunnerFactory.getFectory().getInstance(this.getType(), this.getMethod()).run(this);
        if(!response.equals("_FAILURE")){
            this.setResponseFields(response);
            if(Boolean.parseBoolean(GlobalSettings.getProperty("resplog.info"))){
                if(this.getReturnType().equals(APIConstant.API_RESPONSE_JSON)){
                    result = JSON.toJSONString(JSONObject.parseObject(response), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
                    Log.info("json返回报文：" + result );
                }else{
                    Log.info("返回报文：\n" + response );
                }
            }
        }
        return APIConstant.API_TRANSCODE_FAILED;
    }
    @Override
    public String getType() {
        return APIConstant.API_TYPE_RESTAPI;
    }

    @Override
    public String getUrl() {
        return GlobalSettings.getProperty("http.url")+getSendUri();
    }

    @Override
    public String getCharSet() {
        return APIConstant.API_CHARSET_UTF8;
    }



}
