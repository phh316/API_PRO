package com.domain.api.core;

import com.domain.api.method.HttpGetRunner;
import com.domain.api.method.RestAPIPutRunner;

/**
 * Created by pei hao on 2021/8/13.
 * 接口工厂 懒汉式
 */
public class APIRunnerFactory {

    private static APIRunnerFactory factory = null;

    public static APIRunnerFactory getFectory(){
        if (factory == null){
            return new APIRunnerFactory();
        }
        return factory;
    }

    public IAPIRunner getInstance(String type,String method){
        if(APIConstant.API_TYPE_HTTP.equals(type)){
            if (APIConstant.API_METHOD_POST.equals(method)) {
                return new HttpGetRunner();
            }else if(APIConstant.API_METHOD_GET.equals(method)){
                return new HttpGetRunner();
            }else if(APIConstant.API_METHOD_PUT.equals(method)){
                return new RestAPIPutRunner();
            }
        }
      return null;
    }
}
