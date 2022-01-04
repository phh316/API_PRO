package com.domain.test;

import com.domain.entity.A01825368;
import com.domain.entity.A018253681;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by pei hao on 2021/9/1.
 */
public class A018253681Case {

    @Test(priority = 0)
    public void test001(){
        A01825368 a01825368= new A01825368();
        a01825368.cookie="11111";
        a01825368.run();
        Assert.assertEquals(a01825368.name,"李明");
    }
    @Test(priority = 1)
    public void test002(){
        A018253681 a018253681= new A018253681();
        a018253681.run();
        Assert.assertEquals(a018253681.name,"qqqq");
    }


}
