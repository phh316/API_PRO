package com.domain.test;

import com.domain.api.utils.GlobalSettings;
import com.domain.api.utils.Log;
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
        a018253683.run();
        Assert.assertEquals(a018253683.transnum,544568);
    }

}
