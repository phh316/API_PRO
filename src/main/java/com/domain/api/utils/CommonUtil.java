package com.domain.api.utils;

import com.domain.api.annotation.APIAsQueryString;
import com.domain.api.core.APIConstant;
import com.domain.api.core.AbstractAPIBaseObject;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.ParsingDetector;
import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpHost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pei hao on 2021/8/21.
 */
public class CommonUtil {

    /**
     * 获取文件格式
     * @param f
     * @return
     */
    public static String detectCharSet(File f){
        Log.info("获取文件格式: "+f.getName().substring(f.getName().indexOf(".")+1,f.getName().length()));
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        Charset charset = null;
        try {
            charset = detector.detectCodepage(f.toURL());
            if(charset!= null){
                String name = charset.name();
                if ("void".equals(name)){
                    Log.info("文件格式为："+ APIConstant.API_CHARSET_UTF8);
                    return APIConstant.API_CHARSET_UTF8;
                }else{
                    Log.info("文件格式为："+ APIConstant.API_CHARSET_GBK);
                    return APIConstant.API_CHARSET_GBK;
                }
            }else {
                Log.info("文件格式为："+ APIConstant.API_CHARSET_GBK);
                return APIConstant.API_CHARSET_GBK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.info("文件格式为："+ APIConstant.API_CHARSET_GBK);
        return APIConstant.API_CHARSET_GBK;
    }

    /**
     * 获取文件编码格式
     * @param filepath
     * @return
     */
    public static String detectCharSet(String filepath){
        File f = new File(filepath);
        return detectCharSet(f);
    }

    /**
     * 从txt中获取并转化为String
     * @param file
     * @param charSet
     * @return
     */
    public static String getStringFromTxt(File file,String charSet){
        StringBuffer subffer = new StringBuffer();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file),charSet);
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            while ((line = reader.readLine())!=null){
                subffer.append(line);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return subffer.toString().replaceAll("[\\t\\n\\r]","");
    }
    /**
     * 从txt中获取并转化为String
     * @param file
     * @return
     */
    public static String getStringFromTxt(File file){
        StringBuffer subffer = new StringBuffer();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            while ((line = reader.readLine())!=null){
                subffer.append(line);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return subffer.toString().replaceAll("[\\t\\n\\r]","");
    }

    /**
     * 从txt中获取并转化为map
     * @param file
     * @return
     */
    public static Map<String, String> getMapFromTxt(File file){
        Map<String,String> map = new HashMap<String, String>();
        String str = getStringFromTxt(file);
        if(str ==null || "".equals(str)||str.indexOf("=")<=0){
            String[] values = str.split("&");
            for (String keyvalue:values) {
                String key = keyvalue.split("=")[0];
                String value = keyvalue.split("=")[1];
                map.put(key,value);
            }
        }
        return map;
    }

    /**
     * 获取请求参数
     * @param obj
     * @return
     */
    public static String getQueryString(AbstractAPIBaseObject obj) throws IllegalAccessException {
        Field[] allField = obj.getFieldsByAnno(APIAsQueryString.class);
        String querystr = "";
        for (Field f:allField) {
            f.setAccessible(true);
            APIAsQueryString asinput = f.getAnnotation(APIAsQueryString.class);
            String para = f.getName();
            if (!"".equals(asinput.name()))para = asinput.name();
            querystr+=para+"="+urlEncoderQueryPara(""+f.get(obj))+"&";
        }
        if ("".equals(querystr)){
            return querystr;
        }else {
            Log.info("请求参数为：["+querystr.substring(0,querystr.length()-1)+" ]");
            return querystr.substring(0,querystr.length()-1);
        }
    }

    /**
     * 如果url参数值含有特殊字符时，需要使用 url 编码
     * @param msg
     * @return
     */
    public static String urlEncoderQueryPara(String msg){
        String decodeMsg = null;
        boolean beEnCode = true;
        try {
            decodeMsg = URLDecoder.decode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            beEnCode = false;
        }
        int x = msg.indexOf("+");
        String decodeMsg2 = msg.replaceAll("/+"," ");
        if(beEnCode==false||decodeMsg==null||decodeMsg.equals(msg)||(x>0 && decodeMsg2.equals(msg))){
            beEnCode = false;
        }
        if (beEnCode) {
            return msg;
        }
        try {
            msg = URLEncoder.encode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static String decode(String msg,String decode,String returnType){
        try {
            if (APIConstant.API_RESPONSE_XML.equals(returnType)) {
                return XmlUtil.replaceTransChar(URLDecoder.decode(msg, decode));
            }else if (APIConstant.API_RESPONSE_JSON.equals(returnType)) {
                return replacePrefixChar(msg);
            }else if (APIConstant.API_RESPONSE_HTML.equals(returnType)) {
                return XmlUtil.replacePrefixHtmlChar(msg);
            }else {
                return msg;
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return msg;
        }
    }

    public static String replacePrefixChar(String msg) {
        if (msg == null ||"".equals(msg)) {
            return msg;
        }else {
            msg = msg.trim();
        }
        return msg.startsWith("[")&&msg.endsWith("]")?msg.substring(1,msg.length()-1):msg;
    }

    /**
     * 设置代理，方便fillder抓包
     * @param client
     */
    public static void detectAndSetProxy(DefaultHttpClient client){

        String open = GlobalSettings.getProperty("http.proxy.open");
        if (open.equals("true")){
            String ip = GlobalSettings.getProperty("http.proxy.ip");
            int port = Integer.parseInt(GlobalSettings.getProperty("http.proxy.port"));
            Log.info("proxy_ip: "+ip+" proxy_port:" + port);
            HttpHost pro = new HttpHost(ip,port);
            client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY,pro);
            return;
        }
        return;
    }

    /**
     * 设置代理，方便fillder抓包
     * @param client
     */
    public static void detectAndSetProxy(HttpClient client) {

        String open = GlobalSettings.getProperty("http.proxy.open");
        if (open.equals("true")) {
            String ip = GlobalSettings.getProperty("http.proxy.ip");
            int port = Integer.parseInt(GlobalSettings.getProperty("http.proxy.port"));
            Log.info("proxy_ip: " + ip + " proxy_port:" + port);
            client.getHostConfiguration().setProxy(ip, port);
        }
    }
}
