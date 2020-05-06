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
    public MyApplicationContext(Class<?> configClass) throws IOException,IllegalAccessException, ClassNotFoundException, InstantiationException {
        if(configClass == null ){
            System.out.println("配置类不能为空……");
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
    public void doParse(Class<?> configClass) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
        if(componentScan == null){
            System.out.println("ComponentScan is null,initialize failed……");
            return;
        }
        String packageName = componentScan.value();
        if(null == packageName){
            packageName = "com.study";
        }
        doScan(packageName);
    }


    public void doScan(String strPath) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

        String[]paths = strPath.split(",");
        for (String path : paths) {
            String s = path.replace(".","/");
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
                        for (String className : fileNames) {
                            className = path+"."+className.replace(".class","");
                            Annotation serviceAnnotation = Class.forName(className).getAnnotation(Service.class);
                            Annotation daoAnnotation = Class.forName(className).getAnnotation(Repository.class);
                            if(serviceAnnotation != null || daoAnnotation != null){
                                Object o = contextMap.get(className);
                                o = o == null ? newInstance(className):o;
//                                Field[] declaredFields = o.getClass().getDeclaredFields();
//                                for (Field field : declaredFields) {
//                                    if(field.getAnnotation(Autowired.class) != null){
//                                        Class<?> fieldType = field.getType();
//                                        Object fieldObject = getByFieldType(fieldType);
//                                        field.setAccessible(true);
//                                        field.set(o,fieldObject);
//                                    }
//                                }
                                contextMap.put(className,o);
                            }
                        }

                    }
                }
            }

        }

        for(String key : contextMap.keySet()){
            System.out.println(key);
        }
    }


    private Object newInstance(String className){
        if(className == null || "".equals(className)){
            System.out.println("实例话异常，className is null");
            return null;
        }

        if(contextMap.get(className) != null){
            return contextMap.get(className);
        }
        try {
            Object o = Class.forName(className).newInstance();


            Field[] declaredFields = o.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if(field.getAnnotation(Autowired.class) != null){
                    Class<?> fieldType = field.getType();
                    Object fieldObject = getByFieldType(fieldType);
                    field.setAccessible(true);
                    field.set(o,fieldObject);
                }
            }

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


    private Object getByFieldType(Class<?> fieldType){
        for(String key : contextMap.keySet()){
            Object o = contextMap.get(key);
            if(o.getClass().getInterfaces()[0] == fieldType){
                return o;
            }
        }
        return  null;

    }


    public Object getObject(String className){
        return contextMap.get(className);
    }

}
