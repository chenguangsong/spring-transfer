package com.study.test;

import com.study.config.JavaConfig;
import com.study.core.MyApplicationContext;
import com.study.service.AcountServiceImpl;
import com.study.service.IAccountService;
import org.junit.Test;

/**
 * @ClassName MainTest
 * @Description 转账测试类
 * @Author chenguang
 * @Date 2020-05-06 13:57
 * @Version 1.0
 **/
public class MainTest {

    @Test
    public void mainTest() throws Exception {
        //1.根据Java配置类实例话容器对象
        MyApplicationContext myApplicationContext = new MyApplicationContext(JavaConfig.class);
        //2.从容器中获取 AcountServiceImpl 实例
        IAccountService service = (IAccountService) myApplicationContext.getObject(AcountServiceImpl.class);
        //3.执行转账操作
        service.updateAccount("200001","200002",100);
    }

}
