package com.domain.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by pei hao on 2021/8/21.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XmlAsInPut {

    /**
     * 接口参数名
     */
    String desc() default "";

    /**
     * 接口参数名
     */
    String name() default "";
    /**
     * 接口定位路径
     */
    String xpath() default "";
    /**
     * 接口定位路径
     */
    boolean isCDATA() default false;

}
