package com.domain.api.http;

import com.domain.api.core.APIConstant;
import com.domain.api.core.APIRunnerFactory;
import com.domain.api.core.AbstractAPIBaseObject;
import com.domain.api.utils.GlobalSettings;
import com.domain.api.utils.Log;

/**
 * Created by pei hao on 2021/9/1.
 */
public abstract class HttpAPIObject extends AbstractAPIBaseObject {
    /**
     * 构造方法
     */
    public HttpAPIObject() {
        super();
    }

    @Override
    public String run() {
        this.setRequestFileds();
        Log.info("获取到的uri:" + this.getUrl());
        String response = APIRunnerFactory.getFectory().getInstance(this.getType(), this.getMethod()).run(this);
        if (!response.equals("_FAILURE")) {
            this.setResponseFields(response);
            if (Boolean.parseBoolean(GlobalSettings.getProperty("resplog.info"))) {
                Log.info("接口返回报文如下：" + response);
                return processResponse(response);
            }
        }
        return APIConstant.API_TRANSCODE_FAILED;
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
        return GlobalSettings.getProperty("http.url")+getSendUri();
    }

    public  abstract String getCharSet();
    public  abstract String getMethod();
    public  abstract String getSendUri();
}
