package com.bjsxt.sorm.core;

import com.bjsxt.sorm.bean.ColumnInfo;
import com.bjsxt.sorm.bean.TableInfo;
import com.bjsxt.sorm.utils.JavaFileUtils;
import com.bjsxt.sorm.utils.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 负责获取管理数据库所有表结构和类结构的关系，并可以根据表结构生成类结构
 * @author TianXiang
 * */
public class TableContext {
    //key表名   value表信息
    public static Map<String,TableInfo> tables = new HashMap<>();
    //将po的Class对象和表信息关联起来
    public static Map<Class,TableInfo> poClassTableMap = new HashMap<>();

    private TableContext() {
    }

    static {
        Connection con = null;
        try {
            con = DBManager.getConnection();
            //数据库源信息
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet tableRet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
            while (tableRet.next()){
                String tableName = (String) tableRet.getObject("TABLE_NAME");
                TableInfo ti = new TableInfo(tableName,new ArrayList<ColumnInfo>(),new HashMap<String,ColumnInfo>());
                tables.put(tableName,ti);
                //查询表中的所有字段
                ResultSet set = dbmd.getColumns(null, "%", tableName, "%");
                while (set.next()){
                    ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"), set.getString("TYPE_NAME"), 0);
                    ti.getColumns().put(set.getString("COLUMN_NAME"),ci);
                }
                //查询表中的主键
                ResultSet set2 = dbmd.getPrimaryKeys(null, "%", tableName);
                while (set2.next()){
                    ColumnInfo ci2 = ti.getColumns().get(set2.getObject("COLUMN_NAME"));
                    ci2.setKeyType(1);
                    ti.getPriKeys().add(ci2);
                }
                //取唯一主键，方便使用，如果是联合主键，则为空
                if(ti.getPriKeys().size()>0){
                    ti.setOnlyPriKey(ti.getPriKeys().get(0));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(con);
        }
        //加载po类
        updateJavaPOFile();
        //加载po下的所有类
        loadPOTables();
    }

    public static Map<String,TableInfo> getTableInfos(){
        return tables;
    }

    //表结构到类结构
    public static void updateJavaPOFile(){
        Map<String, TableInfo> map = TableContext.getTableInfos();
        for (Map.Entry<String, TableInfo> entry : map.entrySet()) {
            JavaFileUtils.createJavaPOFile(entry.getValue(), new MySqlTypeConverter());
        }
    }

    //加载po包下面的类
    public static void loadPOTables(){
        for (TableInfo tableInfo : tables.values()) {
            try {
                Class<?> c = Class.forName(DBManager.getConf().getPoPackage() + "." + StringUtils.firstChar2UpperCase(tableInfo.getTname()));
                poClassTableMap.put(c,tableInfo);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /*//根据表结构，更新指定的PO类
    //根据配置文件的srcPath和poPackage
    public static void updatePO(){
        Map<String, TableInfo> tables = TableContext.getTableInfos();
        for (TableInfo t: tables.values()) {
            JavaSRCUtil.createJavaPOFile(t,new MySqlTypeConvertor());
        }
    }

    //加载po包下面的po类
    public static void loadPOTables(){
        Map<String, TableInfo> tables = TableContext.getTableInfos();
        String poPath = DBManager.pros.getProperty("poPackage");
        for (TableInfo t : tables.values()) {
            try {
                Class c = Class.forName(poPath + "." + StringUtils.firstChar2Upper(t.getTname()));
                poClassTableMap.put(c,t);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }*/

    /*public static void main(String[] args) {
        Map<String, TableInfo> tables = getTableInfos();
        System.out.println(tables);
    }*/

}
