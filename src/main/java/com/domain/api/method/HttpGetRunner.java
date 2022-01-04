package com.domain.api.method;

import com.domain.api.core.APIConstant;
import com.domain.api.core.AbstractAPIBaseObject;
import com.domain.api.core.IAPIRunner;
import com.domain.api.utils.CommonUtil;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by pei hao on 2021/8/26.
 */
public class HttpGetRunner implements IAPIRunner {

    private static final Logger log = Logger.getLogger(HttpGetRunner.class.getName());

    @Override //URI和CharSet
    public String run(AbstractAPIBaseObject obj) {
        HttpClient client = null;
        HttpMethod method = null;
        String msg = APIConstant.API_TRANSCODE_FAILED;
        try{
            client = new HttpClient();
            method = new GetMethod(obj.getUrl());
            client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            client.getParams().setContentCharset(obj.getCharSet());
//            System.out.println(CommonUtil.getQueryString(obj));
//            String str = "age=登录001&disname=peihaohao2021";
//            String smsg =  URLDecoder.decode(str,"UTF-8");
            method.setQueryString(CommonUtil.getQueryString(obj));
//               method.setQueryString(smsg);
//            method.setQueryString(EncodingUtil.formUrlEncode(meta_list, "UTF-8"));
//            method.addRequestHeader("Content-type" , "text/html; charset=UTF-8");
//            System.out.println(CommonUtil.getQueryString(obj));
            client.executeMethod(method);
            System.out.println(method.getStatusCode());
            if(method.getStatusCode() == HttpStatus.SC_OK){
                //获取cookie信息
                Cookie[] cookies = client.getState().getCookies();
                StringBuffer tempCookies = new StringBuffer();
                for (Cookie c: cookies) {
                    tempCookies.append(c.toString()+";");
                }
                obj.setCookie(tempCookies.toString());
                msg = method.getResponseBodyAsString();
            }else{
                log.error("接口返回状态异常，状态吗:"+ method.getStatusCode());
                log.error("接口返回信息:"+ method.getResponseBodyAsString());
            }
            method.releaseConnection();
        }catch (IOException e){
                e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return  CommonUtil.decode(msg,obj.getCharSet(),obj.getReturnType());
    }
}
