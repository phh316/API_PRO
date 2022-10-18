package com.domain.test;


import com.domain.api.utils.ExcelUtil;
import com.domain.api.utils.Log;
import com.domain.entity.A018253681;
import org.testng.Assert;
import org.testng.TestNGException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by pei hao on 2021/9/1.
 */
public class A018253681Case {
    @DataProvider(name = "ExcelData")
    public  Object[][] getData(){
        Object[][] result = null;
        try {
            result = ExcelUtil.getExcelData(new A018253681(),"A018253681");
        }catch (TestNGException e){
            Log.error("获取数据失败");
        }
        return result;
    }

    @Test(dataProvider ="ExcelData",enabled = false)
    public void test001(String id, String caseName, String loginName, String loginPwd,
                        String keyword1, String keyword2,String keyword3)throws Exception{
        A018253681 a01825368= new A018253681();
        a01825368.name="测试";
        a01825368.age="11";
        a01825368.run();
        Assert.assertEquals(a01825368.rpname,"qqqq1");
    }


    @Test
    public void get()throws Exception{
        A018253681 a01825368= new A018253681();
        a01825368.name="测试";
        a01825368.age="11";
        a01825368.run();
        Assert.assertEquals(a01825368.rpname,"qqqq");
    }


}
