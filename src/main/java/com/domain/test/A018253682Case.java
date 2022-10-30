package com.domain.test;

import com.domain.api.utils.ExcelUtil;
import com.domain.api.utils.Log;
import com.domain.entity.A018253682;
import org.testng.Assert;
import org.testng.TestNGException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by pei hao on 2021/9/1.
 */
public class A018253682Case {
//
    @DataProvider(name = "ExcelData")
    public  Object[][] getData(){
        Object[][] result = null;
        try {
            result = ExcelUtil.getExcelData(new A018253682(),"A018253681");
        }catch (TestNGException e){
            Log.error("获取数据失败");
        }
        return result;
    }

    @Test(dataProvider ="ExcelData")
    public void testpost(String id, String caseName, String loginName, String loginPwd,
                        String keyword1, String keyword2,String keyword3)throws Exception{
        A018253682 a01825368= new A018253682();
        a01825368.run();
        Assert.assertEquals(a01825368.rpname,"qqqq");
    }

    @Test(enabled = false)
    public void testpost()throws Exception{
        A018253682 a018253682 = new A018253682();
        a018253682.id = "用户名称";
        a018253682.name = "用户名称1";
        a018253682.age = "用户名称2";
        a018253682.run();
        Assert.assertEquals(a018253682.rpname,"qqqq");
    }

    @Test
    public void json()throws Exception{
        A018253682 a018253682 = new A018253682();
        a018253682.run();
        Assert.assertEquals(a018253682.rpname,"qqqq");
    }

    @Test
    public void json1()throws Exception{
        A018253682 a018253682 = new A018253682();
        a018253682.run();
        Assert.assertEquals(a018253682.rpname,"qqqq");
    }

    @Test
    public void json2()throws Exception{
        A018253682 a018253682 = new A018253682();
        a018253682.run();
        Assert.assertEquals(a018253682.rpname,"qqqq");
    }
    @Test
    public void json3()throws Exception{
        A018253682 a018253682 = new A018253682();
        a018253682.run();
        Assert.assertEquals(a018253682.rpname,"qqqq");
    }

}
