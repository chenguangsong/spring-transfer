package com.study.ustils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName JDKProxy
 * @Description TODO
 * @Author songchenguang
 * @Date 2020-05-06 17:31
 * @Version 1.0
 **/
public class JDKProxy {


    public static Object getProxy(Class<?> object){
        Object o = Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), new Class[]{object}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return  method.invoke(proxy,args);
            }
        });
        return o;
    }

}
