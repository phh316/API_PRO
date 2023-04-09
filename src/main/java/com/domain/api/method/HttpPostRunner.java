package com.domain.api.method;

import com.domain.api.annotation.APIAsHeader;
import com.domain.api.annotation.APIAsInPut;
import com.domain.api.core.APIConstant;
import com.domain.api.core.AbstractAPIBaseObject;
import com.domain.api.core.IAPIRunner;
import com.domain.api.utils.CommonUtil;
import com.domain.api.utils.GlobalSettings;
import com.domain.api.utils.Log;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

/**
 * http post
 */
public class HttpPostRunner implements IAPIRunner {

    private int connectionTimeOut = 6000;
    private int readTimeOut = 6000;

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    private class CustomPostMethod extends PostMethod{

        public CustomPostMethod(String uri){
            super(uri);
        }
    }

    @Override //URI和CharSet
    public String run(AbstractAPIBaseObject obj) {
        HttpClient client = null;
        CustomPostMethod method = null;
        String str = "";
        StringBuffer stringBuffer = new StringBuffer();
        String msg = APIConstant.API_TRANSCODE_FAILED;
        try{
            client = new HttpClient();
            method = new CustomPostMethod(obj.getUrl());

            client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            client.getParams().setContentCharset(obj.getCharSet());

            if (GlobalSettings.getProperty("connectionTimeOut")!= null){
                this.setConnectionTimeOut(Integer.valueOf(GlobalSettings.getProperty("connectionTimeOut")));
            }
            if (GlobalSettings.getProperty("readTimeOut")!= null){
                this.setConnectionTimeOut(Integer.valueOf(GlobalSettings.getProperty("readTimeOut")));
            }

            method.setQueryString(CommonUtil.getQueryString(obj));
//            StringEntity entity = new StringEntity(obj.getRequestStrfromFile(), obj.getCharSet());
            method.setRequestEntity((RequestEntity)new StringEntity(obj.getRequestStrfromFile(), obj.getCharSet()));

            //获取头信息
            Field[] allHeaders = obj.getFieldsByAnno(APIAsHeader.class);
            for (Field f:allHeaders) {
                f.setAccessible(true);
                APIAsHeader asheader = f.getAnnotation(APIAsHeader.class);
                String para = f.getName();
                if (!"".equals(asheader.name())) {
                    para = asheader.name();
                    if(f.get(obj)!=null && !f.get(obj).equals("")){
                        method.addRequestHeader(para,f.get(obj).toString());
                    }
                    break;
                }
                Log.info("http头参数:[" +para+ " = "+ f.get(obj).toString()+"]");
            }

            //加载文件中的参数
            Map<String, String> parameters = obj.getBodyParameters();
            Iterator<String> iterator = parameters.keySet().iterator();
            while (iterator.hasNext()){
                String key = iterator.next().toString();
                String value = parameters.get(key);
                method.addParameter(key,value);
                Log.info("加载文件上送参数为:["+ key + "=" + value+ "]");
            }

            //获取接口定义的参数
            Field[] allFiled = obj.getFieldsByAnno(APIAsInPut.class);
            for (Field f:allFiled) {
                f.setAccessible(true);
                APIAsInPut asinput = f.getAnnotation(APIAsInPut.class);
                String para = f.getName();
                if (!"".equals(asinput.name())) {
                    para = asinput.name();
                    String value = f.get(obj)!=null?f.get(obj).toString():null;
                    if(value!=null&&!value.equals("")){
                        method.addParameter(para,value);
                        Log.info("接口类定义上送的参数:[ " +para+ " = "+ f.get(obj).toString()+"]");
                    }
                }
            }
            //设置代理，方便抓包
            CommonUtil.detectAndSetProxy(client);
            client.executeMethod(method);
            if(method.getStatusCode()== HttpStatus.SC_OK){
                Cookie[] cookies = client.getState().getCookies();
                StringBuffer tmpcookies = new StringBuffer();
                for (Cookie c : cookies ) {
                    tmpcookies.append(c.toString()+";");
                }
                obj.setCookie(tmpcookies.toString());
                BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
                msg = stringBuffer.toString();
            }else{
                Log.error("接口返回状态吗:["+ method.getStatusCode()+"]");
                Log.error("接口返回信息:["+ method.getResponseBodyAsString()+"]");
            }
        }catch (IOException e){
                e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return  CommonUtil.decode(msg,obj.getCharSet(),obj.getReturnType());
    }
}
