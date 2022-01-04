package com.domain.api.lang;/**
 * Created by pei hao on 2021/9/11.
 */

import com.alibaba.fastjson.JSONObject;

public class JsonElement extends APIElement {

    JSONObject json= null;

    public JsonElement(){
        super();
    }
    public JsonElement(JSONObject json){
        super();
        this.json = json;
    }
    public String toJsonString(){
       return json !=null?json.toJSONString():"" ;
    }
}
