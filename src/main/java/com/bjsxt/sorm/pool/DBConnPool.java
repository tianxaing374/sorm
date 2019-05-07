package com.bjsxt.sorm.pool;

import com.bjsxt.sorm.core.DBManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class DBConnPool {

    private static final int POOL_MAX_SIZE = DBManager.getConf().getPoolMaxSize();
    private static final int POOL_MIN_SIZE = DBManager.getConf().getPoolMinSize();
    public static ArrayBlockingQueue<Connection> pool = new ArrayBlockingQueue<Connection>(POOL_MAX_SIZE);

    static {
        initPool();
    }

    public static void initPool(){

        while (pool.size()<DBConnPool.POOL_MIN_SIZE) {
            try {
                pool.put(DBManager.createConnection());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static Connection getConnection(){
        try {
            if(pool.size()==0){
                pool.put(DBManager.createConnection());
            }
            Connection take = pool.take();
            return take;
        } catch (InterruptedException e) {
            return null;
        }
    }

    public static void close(Connection connection){
        if(pool.size()>=POOL_MAX_SIZE){
            if (connection!=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                pool.put(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
