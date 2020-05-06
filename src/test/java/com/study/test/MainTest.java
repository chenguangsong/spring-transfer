package com.study.test;

import com.study.config.JavaConfig;
import com.study.core.MyApplicationContext;
import com.study.service.IAccountService;
import org.junit.Test;

/**
 * @ClassName MainTest
 * @Description TODO
 * @Author songchenguang
 * @Date 2020-05-06 13:57
 * @Version 1.0
 **/
public class MainTest {




    @Test
    public void mainTest() throws Exception {
        MyApplicationContext myApplicationContext = new MyApplicationContext(JavaConfig.class);
        IAccountService service = (IAccountService) myApplicationContext.getObject("com.study.service.AcountServiceImpl");
        service.updateAccount("200001","200002",500);

    }

}
