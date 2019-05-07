package com.bjsxt.sorm.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 封装了JDBC常用的操作
 * */
public class JDBCUtils {

    public static void handleParams(PreparedStatement ps,Object[] params){
        if(params!=null){
            for (int i = 0; i < params.length; i++) {
                try {
                    ps.setObject(i+1,params[i]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
