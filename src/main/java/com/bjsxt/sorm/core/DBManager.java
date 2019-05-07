package com.bjsxt.sorm.core;

import com.bjsxt.sorm.bean.Configuration;
import com.bjsxt.sorm.pool.DBConnPool;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 根据配置信息，维持连接对象的管理（增加连接池功能）
 *
 * @author TianXiang
 */
public class DBManager {
    private static Configuration conf;

    static {
        Properties pros = new Properties();
        try {
            pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        conf = new Configuration();
        conf.setDriver(pros.getProperty("driver"));
        conf.setPoPackage(pros.getProperty("poPackage"));
        conf.setPwd(pros.getProperty("pwd"));
        conf.setSrcPath(pros.getProperty("srcPath"));
        conf.setUrl(pros.getProperty("url"));
        conf.setUser(pros.getProperty("user"));
        conf.setUsingDB(pros.getProperty("usingDB"));
        conf.setQueryClass(pros.getProperty("queryClass"));
        conf.setPoolMinSize(Integer.valueOf(pros.getProperty("poolMinSize")));
        conf.setPoolMaxSize(Integer.valueOf(pros.getProperty("poolMaxSize")));
        try {
            Class.forName("com.bjsxt.sorm.core.TableContext");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return DBConnPool.getConnection();
    }

    /*public static Connection getConnection() {
        try {
            Class.forName(conf.getDriver());
            //todo 先直接建立连接，后续添加连接池
            return DriverManager.getConnection(conf.getUrl(), conf.getUser(), conf.getPwd());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public static Connection createConnection() {
        try {
            Class.forName(conf.getDriver());
            //todo 先直接建立连接，后续添加连接池
            return DriverManager.getConnection(conf.getUrl(), conf.getUser(), conf.getPwd());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close(ResultSet rs,Statement ps,Connection conn){
        close(ps,conn);
        try {
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement ps,Connection conn){
        close(conn);
        try {
            if(ps!=null){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn){
        DBConnPool.close(conn);
        /*if (conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    public static Configuration getConf(){
        return conf;
    }

}
