package com.bjsxt.sorm.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射常用操作
 * @author TianXiang
* */
public class ReflectUtils {

    public static Object invokeGet(String fieldName,Object obj){
        try {
            Class<?> c = obj.getClass();
            Method method = c.getDeclaredMethod("get" + StringUtils.firstChar2UpperCase(fieldName), null);
            return method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void invokeSet(Object obj,String fieldName,Object value){
        Class<?> c = obj.getClass();
        try {
            if(value!=null){
                Method method = c.getDeclaredMethod("set" + StringUtils.firstChar2UpperCase(fieldName),value.getClass());
                method.invoke(obj,value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
