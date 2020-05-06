package com.study.core;

import com.study.annotation.*;
import com.study.ustils.CGLibProxy;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MyApplicationContext
 * @Description 工厂类，存放bean的实例
 * @Author chenguang
 * @Date 2020-05-05 18:24
 * @Version 1.0
 **/
public class MyApplicationContext {

    //存放Bean实例
    private final ConcurrentHashMap<String,Object> contextMap = new ConcurrentHashMap<String,Object>();

    /**
    * @author chenguang
    * @Description //构造函数
    * @CreateDate 2020-05-05 19:09
    * @Param [configClass]
    * @return
    **/
    public MyApplicationContext(Class<?> configClass) throws IOException, ClassNotFoundException {
        if(configClass == null ){
            System.out.println("配置类不能为空……");
            return;
        }
        doParse(configClass);
    }

    /**
    * @author chenguang
    * @Description //解析注解类
    * @CreateDate 2020-05-05 19:09
    * @Param [configClass]
    * @return void
    **/
    public void doParse(Class<?> configClass) throws IOException, ClassNotFoundException {
        ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
        String  path = "com.study";
        if(componentScan != null && componentScan.value() != null && !"".equals(componentScan.value())){
            path = componentScan.value();
        }
        doScan(path.split(","));
    }


    /**
    * @author chenguang
    * @Description //根据注解扫描，实例化bean
    * @CreateDate 2020-05-06 20:33
    * @Param [strPath]
    * @return void
    **/
    public void doScan(String[] packageNames) throws ClassNotFoundException, IOException {
        for (String packageName : packageNames) {
            String s = packageName.replace(".","/");
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(s);
            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                if(url != null){
                    String protocol = url.getProtocol();
                    if(protocol.equals("file")){
                        String pacakgePath = url.getPath();
                        File file = new File(pacakgePath);
                        String[] fileNames = null;
                        if(file.isDirectory()){
                            fileNames = file.list();
                        }
                        // fileNames : 包下包含的所有文件
                        for (String className : fileNames) {
                            //包名+文件名 = 类的全限定类名
                            className = packageName+"."+className.replace(".class","");
                            newInstance(className);
                        }
                    }
                }
            }
        }
    }

    /**
    * @author chenguang
    * @Description //根据全限定类名实例化Bean并存放到contextMap中
    * @CreateDate 2020-05-06 23:18
    * @Param [className]
    * @return void
    **/
    public void newInstance(String className) throws ClassNotFoundException {
        Annotation serviceAnnotation = getAnnotationByClassName(className,Service.class);
        Annotation daoAnnotation = getAnnotationByClassName(className,Repository.class);
        // 只会实例话包含类Service或Repository注解的类
        if(serviceAnnotation != null || daoAnnotation != null){
            Object o =doNewInstance(className);
            contextMap.put(className,o);
        }
    }

    /**
     * @author chenguang
     * @Description //根据className 全限定类名获取该类上的指定注解
     * @CreateDate 2020-05-06 23:14
     * @Param [className, annotationClass]
     * @return java.lang.annotation.Annotation
     **/
    public Annotation getAnnotationByClassName(String className,Class annotationClass) throws ClassNotFoundException {
        return Class.forName(className).getAnnotation(annotationClass);
    }

    /**
    * @author chenguang
    * @Description //实例化Bean
    * @CreateDate 2020-05-06 20:38
    * @Param [className]
    * @return java.lang.Object
    **/
    private Object doNewInstance(String className){
        if(className == null || "".equals(className)){
            System.out.println("实例话异常，className is null");
            return null;
        }
        //如果之前已经实例化过，直接返回
        if(contextMap.get(className) != null){
            return contextMap.get(className);
        }
        try {
            Object o = Class.forName(className).newInstance();
            Field[] declaredFields = o.getClass().getDeclaredFields();
            //遍历 根据是否有Autowired注解怕断是否有依赖属性，如果有需要为属性赋值
            for (Field field : declaredFields) {
                if(field.getAnnotation(Autowired.class) != null){
                    Class<?> fieldType = field.getType();
                    Object fieldObject = getByFieldType(fieldType);
                    //暴力访问，为属性赋值
                    field.setAccessible(true);
                    field.set(o,fieldObject);
                }
            }
            //如果是事务控制的类，需要cglib生成动态代理
            Transactional transactional = Class.forName(className).getAnnotation(Transactional.class);
            if(transactional != null){
                o = new CGLibProxy().getCglibProxy(o);
            }
            return o;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author chenguang
     * @Description //根据接口类型返回容器中存在的bean实例
     * @CreateDate 2020-05-06 20:40
     * @Param [fieldType]
     * @return java.lang.Object
     **/
    private Object getByFieldType(Class<?> fieldType){
        for(String key : contextMap.keySet()){
            Object o = contextMap.get(key);
            if(o.getClass().getInterfaces()[0] == fieldType){
                return o;
            }
        }
        return  null;
    }



    /**
    * @author chenguang
    * @Description //从容器中获取对象
    * @CreateDate 2020-05-06 20:59
    * @Param [clazz]
    * @return java.lang.Object
    **/
    public Object getObject(Class clazz){
        return contextMap.get(clazz.getName());
    }

}
