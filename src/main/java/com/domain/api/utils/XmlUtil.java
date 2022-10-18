package com.domain.api.utils;

import org.dom4j.Branch;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Iterator;

/**
 * Created by pei hao on 2021/9/1.
 */
public class XmlUtil {

    /**
     * xml中需要处理的转义字符 < > & ' " /
     * @param msg
     * @return
     */
    public static String replaceTransChar(String msg){
        String tmp = msg.replaceAll("&lt:","@lt@")
                        .replaceAll("&gt:","@gt@")
                        .replaceAll("&amp:","@amp@")
                        .replaceAll("&apos:","@apos@")
                        .replaceAll("&quot:","@quot@");

        tmp = tmp.replaceAll("&","&amp");

        tmp = tmp.replaceAll("@lt@:","&lt;")
                .replaceAll("@gt@:","&gt;")
                .replaceAll("@amp@:","&amp;")
                .replaceAll("@apos@:","&apos;")
                .replaceAll("@quot@:","&quot;");
        return tmp;
    }

    /**
     * 对html进行预处理，防止xml异常
     * @param msg
     * @return
     */
    public static String replacePrefixHtmlChar(String msg){

        if (msg == null ||"".equals(msg)) {
            return msg;
        }else if (msg.startsWith("<!DOCTYPE html>")) {
            return msg;
        }else if (msg.indexOf("<!DOCTYPE html>")>0) {
            return msg.substring(msg.indexOf("<!DOCTYPE html>"),msg.length());
        }else if (msg.indexOf("<html>")>0) {
            return msg.substring(msg.indexOf("html>"),msg.length());
        }
        return msg;
    }

    public static Object Dom2Map(Element obj) {
        return "";
    }

    /**
     * 校验XmlAsInput输入
     * @param xpath
     * @return
     */
    public static boolean isAttribute(String xpath){
        if(xpath.indexOf("/")<0){
            return false;
        }
        String str = xpath.substring(xpath.lastIndexOf("/")+1,xpath.length());
        return str.startsWith("@")?true:false;
    }

    /**
     *
     * @param node
     * @return
     */
    public static boolean isCDATA(Node node){
        if(!node.hasContent()){
            return false;
        }
        Iterator iterator = ((Branch) node).content().iterator();
        Node next = (Node)iterator.next();
        if(Node.CDATA_SECTION_NODE==next.getNodeType()){
            return true;
        }
        return false;
    }

}
