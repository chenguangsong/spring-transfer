package com.study.ustils;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


/**
 * @ClassName CGLibProxy
 * @Description cglib动态代理类，控制事务
 * @Author chenguang
 * @Date 2020-05-06 16:40
 * @Version 1.0
 **/
public class CGLibProxy {
    /**
     * 使用cglib动态代理生成代理对象
     * @param obj 委托对象
     * @return
     */
    public Object getCglibProxy(Object obj) {
        return  Enhancer.create(obj.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                try{
                    TransactionManager.begin();
                    result = method.invoke(obj,objects);
                    TransactionManager.commit();
                }catch (Exception e) {
                    e.printStackTrace();
                    // 抛出异常便于上层servlet捕获
                    TransactionManager.rollBack();
                    throw e;
                }
                return result;
            }
        });
    }
}
