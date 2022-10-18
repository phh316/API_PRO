package com.domain.api.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import net.minidev.json.JSONValue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pei hao on 2021/9/4.
 */
public class JsonUtil {

    public static void outputJsonJavaBean(String json,String javaBeanName,
        String outputDir){
            List<String> keys = getKeys(json);
            String text = getJavaText(javaBeanName,keys);
            writeJavaFile(text,javaBeanName+".java",outputDir);
        }

    /**
     * 添加固定前缀
     * @param json
     * @return
     */
    public static String prefixJson(String json){
        return "response:"+json+"}";
    }

    /**
     * 遍历Json
     * @param json
     * @return
     */
    private static List<String> getKeys(String json) {
        List<String> ls = new ArrayList<>();
        return ls;
    }

    /**
     *将json 转化为map
      * @param json
     * @return
     */
    public static Map<String,Object>convertJson2Map(String json){
        Object obj = JSONValue.parse(json);
        Map<String, Object> map = (Map) obj;
        return map;
    }
    /**
     *将map转化为json
     * @param map
     * @return
     */
    public static String convertMap2Json(Map map){
        return JSONValue.toJSONString(map);
    }

    /**
     * 根据key获取json
     * @param json
     * @param attr
     * @return
     */
    public static Object getAttrValue(String json,String attr) {
        Map<String, Object> map = convertJson2Map(json);
        return map.get(attr);
    }

    public static void writeJavaFile(String text,String fileName,String outputDir) {
        File javafile = new File(outputDir + File.pathSeparator + fileName);
        if (!javafile.exists()) {
            try {
                javafile.createNewFile();
                FileWriter writer = new FileWriter(javafile, false);
                BufferedWriter bufferwriter = new BufferedWriter(writer);
                bufferwriter.write(text);
                bufferwriter.flush();
                bufferwriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static String getJavaText(String className,List<String>keys) {
        StringBuffer str = new StringBuffer();
        str.append("package com.webservice.json.test");
        str.append("\n\r");
        str.append("import com.abc");
        str.append("\n\r");
        str.append("public class "+className+"{");
        for (String key: keys) {
            str.append("@Atturibute");
            str.append("\n");
            str.append("public class "+key+";");
            str.append("\n\r");
        }
             str.append("}");
        return str.toString();
    }

    private static void setJsonValue(Object jsonObj,String jsonPath,Object value){
        if (value.getClass().toString().contains("List") || value.getClass().toString().contains("Map")) {
            value = JSON.toJSON(value);
        }else {
            value = value.toString();
        }
        boolean isOk = JSONPath.set(jsonObj, jsonPath, value);
        if (!isOk) {
            if (jsonPath.length()>2 && !jsonPath.substring(1,2).equals("[")) {
                String newJsonPath = "$."+jsonPath.substring(1);
                System.out.println("使用当前的 json path【"
                                    +jsonPath
                                    +"】 无法为json串【"
                                    +jsonObj.toString()
                        +"】中定位元素并正常赋值，尝试将其修改为【"
                        +newJsonPath
                        +"】，再进行赋值。\r\n"
                        +"收到该提示，请再检查是否出在【$key.subkey】的用法，若存在请将其修改为【$.key.subkey】");
                        JSONPath.set(jsonObj,newJsonPath,value);
            }
        }
    }
    public static String setJsonValue(String json,String jsonPath,String newValue){
        return json!=null && !"".equals(json) ? json.replaceAll(getKeyAndValue(json,jsonPath),getNewValue(jsonPath,newValue)):"";
    }
    private static String getNewValue(String jsonpath, String newValue){
        return "\""+getKeyFromPath(jsonpath) +"\":\""+newValue+"\"";
    }
    private static String getKeyAndValue(String json, String jsonpath){
        return "\""+getKeyFromPath(jsonpath)+"\":\""+ JSONPath.read(json,jsonpath)+"\"";
    }
    private static String getKeyFromPath(String jsonpath){
        if (jsonpath != null && !"".equals(jsonpath)) {
            String tmp = jsonpath.substring(jsonpath.lastIndexOf(".")+1,jsonpath.length());
            return tmp.indexOf("[") >=0 ? tmp.substring(0,tmp.indexOf("[")):tmp;

        }else {
            return "";
        }
    }
}
