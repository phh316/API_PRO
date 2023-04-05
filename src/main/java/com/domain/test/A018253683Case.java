package com.domain.test;

import com.domain.entity.A018253683;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by pei hao on 2021/9/1.
 */
public class A018253683Case {

    @Test
    public void xml()throws Exception{
        A018253683 a018253683 = new A018253683();
        a018253683.title1="2wqw";
        a018253683.run();
        //测试
        Assert.assertEquals(a018253683.title,200);
    }

}
