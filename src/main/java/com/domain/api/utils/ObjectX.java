package com.domain.api.utils;


import com.alibaba.fastjson.JSONObject;
import com.domain.api.lang.DocElement;
import com.domain.api.lang.JsonElement;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
//        if (isDocElementField(typeName)) {
//            if (isList(valueobj)) {
//                List<Element> ls = (List<Element>) valueobj;
//                return new DocElement(ls,get(0));
//            }
//        }
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
                return Integer.parseInt(valueobj.toString());
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
