package com.domain.api.utils;


import com.alibaba.fastjson.JSONObject;
import com.domain.api.lang.DocElement;
import com.domain.api.lang.JsonElement;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by pei hao on 2021/9/11.
 */
public class ObjectX {

    public ObjectX(){

    }
    public static void setFieldsProperty(Field f, Object thisobj, Object valueobj){
        try {
            f.set(thisobj,transferObject2Value(f,valueobj));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static Object transferObject2Value(Field f, Object valueobj) {
        if (valueobj == null) {
            return valueobj;
        }
        String typeName = f.getType().getName();
        if (isIntField(typeName)) {
            return getObjectIntValue(valueobj);
        }
        if (isDoubleField(typeName)) {
            return getObjectDoubleValue(valueobj);
        }
        if (isFloatField(typeName)) {
            return getObjectFloatValue(valueobj);
        }
        if (isShortField(typeName)) {
            return getObjectShortValue(valueobj);
        }
        if (isLongField(typeName)) {
            return getObjectLongValue(valueobj);
        }
        if (isByteField(typeName)) {
            return getObjectByteValue(valueobj);
        }
        if (isStringField(typeName)) {
            return getObjectStringValue(valueobj);
        }
        if (isListField(typeName)) {
            if (isList(valueobj)) {
                List ls = (List) valueobj;
                if (ls.size()>0) {
                    if (isDomElement(ls.get(0))) {
                        List<Object> list = new ArrayList<>();
                        for (Object obj:ls) {
                            list.add(XmlUtil.Dom2Map((Element)obj));
                        }
                        return list;
                    }
                }
                return valueobj;
            }
        }
        if (isMapField(typeName)) {
            if (isMap(valueobj)) {
                return valueobj;
            } else if (isList(valueobj)){
                List ls = (List) valueobj;
                if (ls.size()>0) {
                    Object valueobj2 = ls.get(0);
                    if (isDomElement(valueobj2)) {
                        return XmlUtil.Dom2Map((Element)ls.get(0));
                    }
                }
                return valueobj;
            }
        }
        if (isJsonElementField(typeName)) {
            if (isJsonElement(valueobj)) {
                JsonElement obj = new JsonElement((JSONObject) valueobj);
                return obj;
            }
        }
        return valueobj;
    }


    public static boolean isDocElementField(String type){
        return "com.domain.api.lang.JsonElement".equals(type);
    }
    public static boolean isJsonElementField(String type){
        return "com.domain.api.lang.DocElementField".equals(type);
    }
    public static boolean isListField(Object type) {
        return "java.util.List".equals(type);
    }
    public static boolean isStringField(Object type) {
        return "java.lang.String".equals(type);
    }
    public static boolean isMapField(Object type) {
        return "java.util.Map".equals(type);
    }
    public static boolean isIntField(Object type) {
        return "int".equals(type);
    }
    public static boolean isDoubleField(Object type) {
        return "double".equals(type);
    }
    public static boolean isFloatField(Object type) {
        return "float".equals(type);
    }
    public static boolean isShortField(Object type) {
        return "short".equals(type);
    }
    public static boolean isLongField(Object type) {
        return "long".equals(type);
    }
    public static boolean isByteField(Object type) {
        return "byte".equals(type);
    }

    public static String getObjectStringValue(Object valueobj) {
        if (isPrimitive(valueobj)) {
            return String.valueOf(valueobj);
        }else if(isString(valueobj)){
            return valueobj.toString();
        }else if(isList(valueobj)){
            List ls = (List) valueobj;
            if (ls.size()==0){
                return "";
            }else if(ls.size()==1){
               Object obj =  ls.get(0);
               if (isDomElement(obj)){
                   return ((Element)obj).getTextTrim();
               }
               if (isDomAttribute(obj)){
                    return ((Attribute)obj).getValue();
               }
            }
        }else if(isDocElement(valueobj)){
            return ((DocElement)valueobj).getTextTrim();
        }
        return "";
    }

    /**
     *
     * @param doc
     * @param xpath
     * @param value
     * @param iscdata
     */
    public static void setXMLElementByFieldValue(Document doc, String xpath, Object value, boolean iscdata){
        Object node = doc.selectNodes(xpath).get(0);
        if(XmlUtil.isAttribute(xpath)){
            Attribute attribute = (Attribute) node;
            attribute.setName(value.toString());
        }else{
            Element attribute = (Element) node;
            if(iscdata){
                attribute.clearContent();
                attribute.addCDATA(value.toString());
            }else {
                if(XmlUtil.isCDATA((Node)attribute)){
                    attribute.clearContent();
                    attribute.addCDATA(value.toString());
                }else{
                    if(value instanceof Map){
                        setXMLElementByMap(doc,xpath,(Map)value);
                    }else if(value instanceof List){
                        setXMLElementByList(doc,xpath,(List)value);
                    }else{
                        attribute.setText(value.toString());
                    }
                }
            }
        }
    }

    /**
     *
     * @param doc
     * @param parnetpath
     * @param map
     */
    private static void setXMLElementByMap(Document doc, String parnetpath,Map map){
        Set<Map.Entry<Object,Object>> set =  map.entrySet();
        for (Map.Entry<Object,Object> ety : set) {
            String keyName = ety.getKey().toString();
            Object keyValue = ety.getValue();
            String xpath = parnetpath + "/" + keyName;
            setXMLElementByFieldValue(doc,xpath,keyValue,false);
        }
    }

    /**
     *
     * @param doc
     * @param parnetpath
     * @param list
     */
    private static void setXMLElementByList(Document doc, String parnetpath,List list){
        for (int i = 0; i < list.size() ; i++) {
            Object obj = list.get(i);
            if (obj instanceof Map){
                setXMLElementByMapInList(doc,parnetpath,(Map)obj,i);
            }else if(obj instanceof List){
                setXMLElementByList(doc, parnetpath,(List)obj);
            }else {
                setXMLElementByStringInList(doc, parnetpath,obj.toString(),i);
            }

        }
    }

    /**
     *
     * 若list输入的类型是Sring,通过该法设置
     * @param doc
     * @param path
     * @param value
     * @param index
     */
    public static void setXMLElementByStringInList(Document doc, String path, String value,int index){
        String parentXPath = path.substring(0, path.lastIndexOf("/"));
        Element parentNodes = (Element)doc.selectNodes(parentXPath).get(0);
        if(index == 0){
            List nodes = doc.selectNodes(path);
            for (Object obj : nodes) {
                ((Element)obj).detach();
            }
            parentNodes.setText(parentNodes.getTextTrim());
        }
        String elementName = path.substring(path.lastIndexOf("/") + 1, path.length());
        Element childrenElement = parentNodes.addElement(elementName);
        childrenElement.setText(value);
    }

    /**
     * List类型字段中的元素是Map 设置Xml报文
     * @param doc
     * @param parnetpath
     * @param map
     * @param index
     */
    private static void setXMLElementByMapInList(Document doc, String parnetpath,Map map,int index){
        Element parentNodes = (Element) doc.selectNodes(parnetpath).get(0);
        if(index == 0){
            Set<Map.Entry<Object,Object>> set =  map.entrySet();
            for (Map.Entry<Object,Object> ety : set) {
                String keyName = ety.getKey().toString();
                String xpath = parnetpath + "/" + keyName;
                List nodes = doc.selectNodes(xpath);
                for (Object objNode : nodes) {
                    ((Element)objNode).detach();
                }
            }
            parentNodes.setText(parentNodes.getTextTrim());
        }
        addXMLElementByMap(parentNodes,parnetpath,map);
    }

    /**
     * 通过map设置xml报文
     * @param parentNodes
     * @param path
     * @param map
     */
    private static void addXMLElementByMap(Element parentNodes, String path,Map map){
            Set<Map.Entry<Object,Object>> set =  map.entrySet();
            for (Map.Entry<Object,Object> ety : set) {
                String keyName = ety.getKey().toString();
                Object keyValue = ety.getValue();
                String elementName = keyName;
                Element childrenElement = parentNodes.addElement(elementName);
                String xpath = path + "/" + keyName;
                if (keyValue instanceof Map){
                    addXMLElementByMap(childrenElement,xpath,(Map)keyValue);
                }else if(keyValue instanceof List){
                    addXMLElementByList(childrenElement,xpath,(List)keyValue);
                }else {
                    childrenElement.setText(keyValue.toString());
                }
            }
    }

    /**
     * 通过Lit类型设置xml报文
     * @param parentNodes
     * @param path
     * @param list
     */
    private static void addXMLElementByList(Element parentNodes, String path,List list){
        for (Object obj : list) {
            if (obj instanceof Map){
                addXMLElementByMap(parentNodes,path,(Map)list);
            }else if(obj instanceof List){
                addXMLElementByList(parentNodes,path,list);
            }else {
                parentNodes.setText(obj.toString());
            }
        }
    }

    public static long getObjectByteValue(Object valueobj) {
        if (isPrimitive(valueobj)) {
            return Byte.parseByte(valueobj.toString());
        }else if(isString(valueobj)){
            return Byte.parseByte(valueobj.toString());
        }else if(isList(valueobj)){
            List<Element> list = (List<Element>) valueobj;
            if(list.size()==1){
                return Byte.parseByte(list.get(0).getText());
            }
        }else if(isDocElement(valueobj)){
            return Byte.parseByte(((DocElement)valueobj).getTextTrim());
        }
        return 0;
    }

    public static long getObjectLongValue(Object valueobj) {
        if (isPrimitive(valueobj)) {
            return Long.valueOf(valueobj.toString());
        }else if(isString(valueobj)){
            return Long.valueOf(valueobj.toString());
        }else if(isList(valueobj)){
            List<Element> list = (List<Element>) valueobj;
            if(list.size()==1){
                return Long.parseLong(list.get(0).getText());
            }
        }else if(isDocElement(valueobj)){
            return Long.parseLong(((DocElement)valueobj).getTextTrim());
        }
        return 0;
    }
    public static short getObjectShortValue(Object valueobj) {
        if (isPrimitive(valueobj)) {
            return Short.parseShort(valueobj.toString());
        }else if(isString(valueobj)){
            return Short.parseShort(valueobj.toString());
        }else if(isList(valueobj)){
            List<Element> list = (List<Element>) valueobj;
            if(list.size()==1){
                return Short.parseShort(list.get(0).getText());
            }
        }else if(isDocElement(valueobj)){
            return Short.parseShort(((DocElement)valueobj).getTextTrim());
        }
        return 0;
    }

    public static float getObjectFloatValue(Object valueobj) {
        if (isPrimitive(valueobj)) {
            return Float.parseFloat(valueobj.toString());
        }else if(isString(valueobj)){
            return Float.parseFloat(valueobj.toString());
        }else if(isList(valueobj)){
            List<Element> list = (List<Element>) valueobj;
            if(list.size()==1){
                return Float.parseFloat(list.get(0).getText());
            }
        }else if(isDocElement(valueobj)){
            return Float.parseFloat(((DocElement)valueobj).getTextTrim());
        }
        return 0;
    }

    public static double getObjectDoubleValue(Object valueobj) {
        if (isPrimitive(valueobj)) {
            return Double.parseDouble(valueobj.toString());
        }else if(isString(valueobj)){
            return Double.parseDouble(valueobj.toString());
        }else if(isList(valueobj)){
            List<Element> list = (List<Element>) valueobj;
            if(list.size()==1){
                return Double.parseDouble(list.get(0).getText());
            }
        }else if(isDocElement(valueobj)){
            return Double.parseDouble(((DocElement)valueobj).getTextTrim());
        }
        return 0;
    }

    public static int getObjectIntValue(Object valueobj) {
        if (isPrimitive(valueobj)) {
            return (int)Double.parseDouble(valueobj.toString());
        }else if(isString(valueobj)){
            return Integer.parseInt(valueobj.toString());
        }else if(isList(valueobj)){
            List<Element> list = (List<Element>) valueobj;
            if(list.size()==1){
                return Integer.parseInt(list.get(0).getText());
            }
        }else if(isDocElement(valueobj)){
            return Integer.parseInt(((DocElement)valueobj).getTextTrim());
        }
        return 0;
    }

    public static  boolean isPrimitive(Object object){
        String className = object.getClass().getName();
        return  "java.lang.Integer".equals(className)||
                "java.lang.Long".equals(className)||
                "java.lang.Byte".equals(className)||
                "java.lang.Short".equals(className)||
                "java.lang.Double".equals(className)||
                "java.lang.Float".equals(className);


    }

    public static boolean isString(Object obj){
        return obj instanceof java.lang.String;
    }
    public static boolean isList(Object obj){
        return obj instanceof java.util.List;
    }
    public static boolean isMap(Object obj){
        return obj instanceof java.util.Map;
    }
    public static boolean isDocElement(Object obj){
        return obj instanceof java.lang.String;
    }
    public static boolean isDomElement(Object obj){
        return obj instanceof org.dom4j.Element;
    }
    public static boolean isDomAttribute(Object obj){
        return obj instanceof org.dom4j.Attribute;
    }
    public static boolean isJsonElement(Object obj){
        return obj instanceof net.minidev.json.JSONObject;
    }
}
