package com.domain.api.method;

import com.domain.api.annotation.APIAsHeader;
import com.domain.api.core.APIConstant;
import com.domain.api.core.AbstractAPIBaseObject;
import com.domain.api.core.IAPIRunner;
import com.domain.api.utils.CommonUtil;
import com.domain.api.utils.GlobalSettings;
import com.domain.api.utils.Log;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

/**
 * Created by pei hao on 2021/9/11.
 */
public class RestAPIPostRunner implements IAPIRunner {

    private static final Logger log = Logger.getLogger(RestAPIPostRunner.class.getName());
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
    @Override
    public String run(AbstractAPIBaseObject obj) {
        String msg = APIConstant.API_TRANSCODE_FAILED;
        try{
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(obj.getUrl());
            StringEntity entity = new StringEntity(obj.getRequestStrfromFile(), obj.getCharSet());
            entity.setContentEncoding(obj.getCharSet());
            if (GlobalSettings.getProperty("connectionTimeOut")!= null){
                this.setConnectionTimeOut(Integer.valueOf(GlobalSettings.getProperty("connectionTimeOut")));
            }
            if (GlobalSettings.getProperty("readTimeOut")!= null){
                this.setConnectionTimeOut(Integer.valueOf(GlobalSettings.getProperty("readTimeOut")));
            }
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,getConnectionTimeOut());
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,getReadTimeOut());
            if (APIConstant.API_RESPONSE_JSON.equals(obj.getInputType())){
                entity.setContentType("application/json;charset=UTF-8;");
            }
            if (APIConstant.API_RESPONSE_XML.equals(obj.getInputType())){
                entity.setContentType("application/xml;charset=UTF-8;");
            }

            post.setEntity(entity);

            Field[] allHeaders = obj.getFieldsByAnno(APIAsHeader.class);
            for (Field f:allHeaders) {
                f.setAccessible(true);
                APIAsHeader asheader = f.getAnnotation(APIAsHeader.class);
                String para = f.getName();
                if (!"".equals(asheader.name())) {
                    para = asheader.name();
                    if(f.get(obj)!=null&&!equals("")){
                        post.setHeader(para,f.get(obj).toString());
                        Log.info("http头参数:[" +para+ " = "+ f.get(obj).toString()+" ]");
                    }
                }
                Log.error("http头参数:[" +para+ " = "+ null +" ]");
                break;
            }

            //设置抓包
            CommonUtil.detectAndSetProxy(client);
            HttpResponse resp = client.execute(post);
            if (resp.getStatusLine().getStatusCode()== HttpStatus.SC_OK) {
                msg = EntityUtils.toString(resp.getEntity());
            }else{
                log.error("接口返回状态异常，状态吗:"+ resp.getStatusLine().getStatusCode());
                log.error("接口返回信息:"+ EntityUtils.toString(resp.getEntity()));
            }

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (HttpHostConnectException e){
            Log.error("连接失败");
            return APIConstant.API_TRANSCODE_FAILED;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
