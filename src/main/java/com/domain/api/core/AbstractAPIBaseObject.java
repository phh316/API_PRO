package com.domain.api.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.domain.api.annotation.*;
import com.domain.api.utils.*;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONValue;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static com.domain.api.utils.CommonUtil.detectCharSet;


/**
 * 底层Api
 */
public abstract class AbstractAPIBaseObject {


    /**
     * 构造方法
     */
    public AbstractAPIBaseObject() {
        loadRequestTemplate();
    }


    private String requestStrfromFile;
    /**
     * 客户端cookie
     */
    private String cookie;
    /**
     * 输入报文标志类型
     */
    private String inputType;
    /**
     * 保存参数的map
     */
    Map<String, String> bodyParameters = new HashMap<String, String>();
    /**
     * 保存全局变量。用于接口自己之间的传输
     */
    private static Map<String, String> globalValues = new HashMap<String, String>();

    /**
     * 读取全局变量，用于接口之间传输
     *
     * @param key
     * @return
     */
    public static String getGloablValues(String key) {
        return globalValues.get(key);
    }

    /**
     * 设置全局变量，用于接口之间传输
     *
     * @param key
     * @return
     */
    public static String setGloablValues(String key, String value) {
        return globalValues.put(key, value);
    }

    /**
     * 清除某个全局变量，用于接口之间传输
     *
     * @param key
     * @return
     */
    public static String removeGloablValue(String key) {
        return globalValues.remove(key);
    }


    public String getRequestStrfromFile() {
        return this.requestStrfromFile;
    }

    public void setRequestStrfromFile(String requestStrfromFile) {
        if (requestStrfromFile == null){
            return;
        }
        this.requestStrfromFile = requestStrfromFile;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public Map<String, String> getBodyParameters() {
        return this.bodyParameters;
    }

    public void setBodyParameters(Map<String, String> bodyParameters) {
        this.bodyParameters = bodyParameters;
    }

    /**
     * 加载同名文件报文模板
     */
    public void loadRequestTemplate() {
        try {
                String jsonfile = "/".concat(this.getClass().getPackage().getName().replace(".", "/")).concat("/").concat(this.getClass().getSimpleName() + ".json");
                URL url = this.getClass().getResource(jsonfile);
                if (url != null) {
                    Log.info("开始加载json模板");
                    this.setInputType(APIConstant.API_RESPONSE_JSON);
                    URI jsonuri = url.toURI();
                    File jf = new File(jsonuri);
                    loadJson(jf);
                }
                String xmlfile = "/".concat(this.getClass().getPackage().getName().replace(".", "/")).concat("/").concat(this.getClass().getSimpleName() + ".xml");
                URL xmlurl = this.getClass().getResource(xmlfile);
                if (xmlurl != null) {
                    Log.info("开始加载xml模板");
                    this.setInputType(APIConstant.API_RESPONSE_XML);
                    URI xmluri = xmlurl.toURI();
                    File xf = new File(xmluri);
                    loadXml(xf);
                }
                String xlsxfile = "/".concat(this.getClass().getPackage().getName().replace(".", "/")).concat("/").concat(this.getClass().getSimpleName() + ".xlsx");
                URL xfile = this.getClass().getResource(xlsxfile);
                out:if (xfile != null) {
                    break out;
                }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 加载指定文件模板
     */
    public void loadRequestTemplate(String fname) {
        if(fname == null||"".equals(fname)){
            return;
        }
        String jsonfile = "/".concat(this.getClass().getPackage().getName().replace(".", "/")).concat("/").concat(fname);
        URL jurl = this.getClass().getResource(jsonfile);
        try {
            if (jurl == null) {
                if (fname.endsWith(".json")){
                    this.setInputType(APIConstant.API_RESPONSE_JSON);
                    URI jsonuri = jurl.toURI();
                    File xf = new File(jsonuri);
                    loadJson(xf);
                } else {
                    this.setInputType(APIConstant.API_RESPONSE_XML);
                    URI xmluri = jurl.toURI();
                    File xf = new File(xmluri);
                    loadXml(xf);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }catch (DocumentException e) {
            e.printStackTrace();
        }catch (URISyntaxException e) {
             e.printStackTrace();
        }
    }
    /**
     * 加载xml文件
     * @param f
     */
    private void loadXml(File f) throws DocumentException {
        Log.info("加载xml文件 :"+f);
        SAXReader reader = new SAXReader();
        reader.setEncoding(detectCharSet(f));
        Document doc = reader.read(f);
        this.setRequestStrfromFile(doc.asXML());
        Log.info("加载完成");

        setFileFields();
    }


    /**
     * 加载json文件
     * @param f
     */
    protected void loadJson(File f) throws FileNotFoundException, UnsupportedEncodingException,IOException {
        Object obj = JSONValue.parse(new InputStreamReader(new FileInputStream(f), detectCharSet(f)));
        if(obj != null){
            this.setRequestStrfromFile(obj.toString());
        }else {
            this.setRequestStrfromFile(CommonUtil.getStringFromTxt(f,detectCharSet(f)));
        }
        setFileFields();
    }

    /**
     * 为标记的file赋值
     */
    protected void setFileFields(){
        Log.info("标记赋值");
        Field[] fields = this.getFieldsByAnno(APIAsInPut.class);
        for (Field f: fields) {
            f.setAccessible(true);
            APIAsInPut input = f.getAnnotation(APIAsInPut.class);
            if(input.fromFie()){
                try {
                    f.set(this,this.getRequestStrfromFile());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Field[] getFields(){
        return this.getClass().getFields();
    }


    public Field[] getFieldsByAnno(Class <? extends Annotation> annotationClass){
        Field[] fields = this.getFields();
        Field[] annofields = null;
        List<Field> afl = new ArrayList<Field>();
        for (Field f : fields) {
            if (f.isAnnotationPresent(annotationClass)){
                afl.add(f);
            }
        }
        annofields = new Field[afl.size()];
        for (int i = 0; i <afl.size() ; i++) {
            annofields[i] = afl.get(i);
        }
        return annofields;
    }

    /**
     * 从文件中加载参数列表和参数
     * @param
     */
    public void loadBodyParameters(String filename){
        if(filename==null||"".equals(filename)){
            return;
        }
        String jsonfile = "/".concat(this.getClass().getPackage().getName().replace(".", "/")).concat("/").concat(filename);
        URL jurl = this.getClass().getResource(jsonfile);
        try {
            if (jurl != null) {
                bodyParameters.clear();
                URI uri = jurl.toURI();
                File jf = new File(uri);
                if (filename.endsWith(".properties")) {
                    loadBodyParametersFromProp(jf);
                } else if (filename.endsWith(".xlsx")) {
                    loadBodyParametersFromXls(jf);
                } else if (filename.endsWith(".txt")) {
                    loadBodyParametersFromTxt(jf);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadBodyParametersFromProp(File file){
        Properties bp = new Properties();
        try {
            bp.load(new InputStreamReader(new FileInputStream(file),"GBK"));
            this.bodyParameters = (Map)bp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void loadBodyParametersFromXls(File file){
        Properties bp = new Properties();
        try {
            bp.load(new InputStreamReader(new FileInputStream(file),"GB2312"));
            this.bodyParameters = (Map)bp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void loadBodyParametersFromTxt(File file){
        Properties bp = new Properties();
        try {
            bp.load(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            this.bodyParameters = (Map)bp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Field[] getAllBodyReqFileds(String value) throws IllegalAccessException {
        Field[] fields = this.getFieldsByAnno(APIAsInPut.class);
        List<Field> afl = new ArrayList<>();
        Field[] annofields = null;
        for (Field f: fields) {
            f.setAccessible(true);
            if(ObjectX.isStringField(f.getType().getName())){
                String fvalue = (String) f.get(this);
                if(value.equals(fvalue)){
                   afl.add(f);
                }
            }
        }
        annofields = new Field[afl.size()];
        for (int i = 0; i <afl.size() ; i++) {
            annofields[i] = afl.get(i);
        }
        return annofields;
    }

    /**
     * 初始化输入报文
     */
    protected  void setRequestFileds(){
        String json =null;
        if (this.getInputType()!= null) {
            try {
                Field[] fileds = this.getAllBodyReqFileds(this.getRequestStrfromFile());
                if (APIConstant.API_RESPONSE_XML.equals(this.getInputType())) {
                    setXmlRequest(this.getRequestStrfromFile());
                    if(GlobalSettings.getProperty("requlog.info").equals("true")){
                        Log.info("待发送的xml报文为："+ this.getRequestStrfromFile());
                    }
                }else if (APIConstant.API_RESPONSE_JSON.equals(this.getInputType())) {
                    setJsonRequest(this.getRequestStrfromFile());
                     json = JSON.toJSONString(JSONObject.parseObject(this.getRequestStrfromFile()), SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue,
                            SerializerFeature.WriteDateUseDateFormat);
                    if(GlobalSettings.getProperty("requlog.info").equals("true")){
                        Log.info("待发送的json报文为：\n"+json);
                    }
                }
                if (fileds != null) {
                    for (Field f : fileds) {
                        f.setAccessible(true);
                        f.set(this,this.getRequestStrfromFile());
                    }

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    protected void setXmlRequest(String xml) {
        if (xml == null||xml.equals("")) {
            return;
        }
        try {
            Document doc = DocumentHelper.parseText(xml);
            Field[] fields = this.getFieldsByAnno(XmlAsInPut.class);
            for (Field f : fields) {
                f.setAccessible(true);
                XmlAsInPut input = f.getAnnotation(XmlAsInPut.class);
                String xpath = input.xpath();
                boolean iscdata = input.isCDATA();
                if (f.get(this)!=null){
                    setXMLElement(doc,xpath,f.get(this),iscdata);
                }else{
                    setFieldsByXpath(doc,f,xpath);
                }

            }
            this.setRequestStrfromFile(doc.asXML());
            fields = this.getFieldsByAnno(APIAsInPut.class);
            for (Field f : fields) {
                f.setAccessible(true);
                String name = f.getName();
                if (name.equals("xmlstring")) {
                    f.set(this,doc.asXML());
                    break;
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param doc
     * @param xpath
     * @param value
     * @param iscdata
     */
    protected void setXMLElement(Document doc,String xpath,Object value,boolean iscdata){
        ObjectX.setXMLElementByFieldValue(doc,xpath,value,iscdata);
    }

    private  void setFieldsByXpath(Document doc,Field f,String path){
        List<Node> list = doc.selectNodes(path);
        if(list ==  null){
            return;
        }
        ObjectX.setFieldsProperty(f,this,list);

    }

    protected void setJsonRequest(String json) {
        if (json == null||json.equals("")) {
            return;
        }
        String jsonToSend = json;
        Field[] fields = this.getFieldsByAnno(JsonAsInPut.class);
        for (Field f : fields) {
            f.setAccessible(true);
            String jsonpath = f.getAnnotation(JsonAsInPut.class).jsonPath();
            try {
                if (f.get(this)!=null){
                    jsonToSend =  setJsonFromField(jsonToSend,f,jsonpath);
                }else{
                    setFieldFromJson(jsonToSend,f,jsonpath);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
         this.setRequestStrfromFile(jsonToSend);
    }

    protected String setJsonFromField(String jsonToSend, Field f, String jsonpath) throws IllegalAccessException {
        String fieldValue = f.get(this).toString();
        return JsonUtil.setJsonValue(jsonToSend,jsonpath,fieldValue);
    }

    /**
     * 回写返回值
     * @param response
     * @return
     */
    protected void setResponseFields(String response){
        if (APIConstant.API_RESPONSE_JSON.equals(this.getReturnType())) {
            setOutPutFieldsByJson(response);
        }else if(APIConstant.API_RESPONSE_XML.equals(this.getReturnType())){
            setOutPutFieldsByXml(response);
        }
    }


    /**
     *
     * @param xml
     */
    protected void setOutPutFieldsByXml(String xml){
        Field[] fields = this.getFieldsByAnno(XmlAsOutPut.class);
        try {
            Document doc = DocumentHelper.parseText(xml);
            for (Field f: fields) {
                f.setAccessible(true);
                XmlAsOutPut outPut = f.getAnnotation(XmlAsOutPut.class);
                String path = outPut.xpath();
                if("".equals(path)){
                    continue;
                }else{
                    setFieldsByXpath(doc,f,path);
                }
            }
        } catch (DocumentException e) {
                Log.error("解析失败");
            }


    }
    /**
     * 
     * @param json
     */
    protected void setOutPutFieldsByJson(String json) {
        Field[] fields = this.getFieldsByAnno(JsonAsOutPut.class);
        for (Field f: fields) {
            f.setAccessible(true);
            JsonAsOutPut outPut = f.getAnnotation(JsonAsOutPut.class);
            String path = outPut.jsonPath();
            if(!"".equals(path)){
                setFieldFromJson(json,f,path);
            }else{
                setFieldFromByKeyName(json,f,outPut);
            }
        }
    }

    protected void setFieldFromJson(String json, Field f, String path) {
        ObjectX.setFieldsProperty(f,this, JsonPath.read(json,path));
    }

    protected void setFieldFromByKeyName(String json, Field f, JsonAsOutPut outPut) {
        String key = f.getName();
        if (!"".equals(outPut.key())) {
            key = outPut.key();
            Map<String, Object> map = JsonUtil.convertJson2Map(json);
            ObjectX.setFieldsProperty(f,this,map.get(key));
        }
    }



    public abstract String run();
    public abstract String getType();
    public abstract String getUrl();
    public abstract String getCharSet();
    public abstract String getReturnType();


}
