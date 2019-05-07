package com.bjsxt.sorm.utils;

/**
 * 封装了字符串常用的操作
 * */
public class StringUtils {

    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String firstChar2UpperCase(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

}
