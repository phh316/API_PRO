package com.domain.test;

import com.domain.api.utils.ExcelUtil;
import com.domain.api.utils.Log;
import com.domain.entity.A018253682;
import org.testng.TestNGException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by pei hao on 2021/9/1.
 */
public class A018253682Case {

    @DataProvider(name = "ExcelData")
    public  Object[][] getData(){
        Object[][] result = null;
        try {
            result = ExcelUtil.getExcelData(new A018253682(), "登录案例");
        }catch (TestNGException e){
            Log.error("获取数据失败");
        }
        return result;
    }

    @Test(dataProvider ="ExcelData")
    public void testLogin001(String id, String caseName, String loginName, String loginPwd,
                             String keyword1, String keyword2, String keyword3)throws Exception{
        A018253682 a01825368= new A018253682();
        a01825368.cookie="11111";
        a01825368.disname =loginName;
        a01825368.age =caseName;
        a01825368.run();
//        Assert.assertEquals(a01825368.name,"qqqq");
    }



}
