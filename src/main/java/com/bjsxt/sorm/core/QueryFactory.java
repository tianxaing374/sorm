package com.bjsxt.sorm.core;

public class QueryFactory {

    private static Class c;

    static {
        try {
            c = Class.forName(DBManager.getConf().getQueryClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private QueryFactory() {
    }

    public static Query createQuery(){
        try {
            return (Query) c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
