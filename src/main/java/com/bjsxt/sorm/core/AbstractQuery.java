package com.bjsxt.sorm.core;

import com.bjsxt.sorm.bean.ColumnInfo;
import com.bjsxt.sorm.bean.TableInfo;
import com.bjsxt.sorm.utils.JDBCUtils;
import com.bjsxt.sorm.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractQuery implements Query {

    /*
    * 采用模板设计模式
    * */
    public Object executeQueryTemplate(String sql,Object[] params,Class clazz,CallBack callBack){
        Connection conn = DBManager.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            JDBCUtils.handleParams(ps,params);
            rs = ps.executeQuery();
            return callBack.doExecuteQuery(conn,ps,rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(rs,ps,conn);
        }
        return null;
    }

    @Override
    public int executeDML(String sql, Object[] params) {
        Connection conn = DBManager.getConnection();
        int count = 0;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            JDBCUtils.handleParams(ps,params);
            System.out.println(sql+" 参数为："+ Arrays.toString(params));
            count = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(ps,conn);
        }
        return count;
    }

    @Override
    public void insert(Object obj) {
        Class<?> c = obj.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        Field[] fs = c.getDeclaredFields();
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(tableInfo.getTname()).append(" (");
        ArrayList<Object> params = new ArrayList<>();
        for (Field f : fs) {
            String fieldName = f.getName();
            Object fieldValue = ReflectUtils.invokeGet(fieldName,obj);
            if(fieldValue!=null){
                params.add(fieldValue);
                sql.append(fieldName).append(",");
            }
        }
        sql.setCharAt(sql.length()-1,')');
        sql.append(" values(");
        for (int i = 0; i < params.size(); i++) {
            sql.append("?,");
        }
        sql.setCharAt(sql.length()-1,')');
        executeDML(sql.toString(),params.toArray());
    }

    @Override
    public void delete(Class clazz, Object id) {
        TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();
        String sql = "delete from "+tableInfo.getTname()+" where "+onlyPriKey.getName()+" = ?";
        executeDML(sql,new Object[]{id});
    }

    @Override
    public void delete(Object obj) {
        Class<?> c = obj.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();
        //通过反射机制
        delete(c,ReflectUtils.invokeGet(onlyPriKey.getName(),obj));
    }

    @Override
    public int update(Object obj, String[] fieldNames) {
        Class<?> c = obj.getClass();
        List<Object> params = new ArrayList<>();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        ColumnInfo priKey = tableInfo.getOnlyPriKey();
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(tableInfo.getTname()).append(" set ");
        for (String fieldName : fieldNames) {
            Object fvalue = ReflectUtils.invokeGet(fieldName, obj);
            sql.append(fieldName).append("=?,");
            params.add(fvalue);
        }
        sql.setCharAt(sql.length()-1,' ');
        sql.append("where ").append(priKey.getName()).append("=?");
        params.add(ReflectUtils.invokeGet(priKey.getName(), obj));
        return executeDML(sql.toString(),params.toArray());
    }

    @Override
    public int update(Object object) {
        Class<?> c = object.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        ColumnInfo priKey = tableInfo.getOnlyPriKey();
        List<String> fieldNames = new ArrayList<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object o = ReflectUtils.invokeGet(field.getName(), object);
            if(o!=null && !priKey.getName().equals(field.getName())){
                fieldNames.add(field.getName());
            }
        }
        String[] strings = new String[fieldNames.size()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = fieldNames.get(i);
        }
        return update(object,strings);
    }

    @Override
    public List queryRows(String sql,final Class clazz, Object[] params) {
        return (List) executeQueryTemplate(sql, params, clazz, new CallBack() {
            @Override
            public Object doExecuteQuery(Connection connection, PreparedStatement ps, ResultSet rs) {
                List list = new ArrayList();
                try {
                    ResultSetMetaData metaData = rs.getMetaData();
                    while (rs.next()){
                        Object rowObj = clazz.newInstance();
                        for (int i = 0; i < metaData.getColumnCount(); i++) {
                            String columnName = metaData.getColumnLabel(i+1);
                            Object columnValue = rs.getObject(i+1);
                            ReflectUtils.invokeSet(rowObj,columnName,columnValue);
                        }
                        list.add(rowObj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return list;
            }
        });
    }

    @Override
    public Object queryUniqueRow(String sql, Class clazz, Object[] params) {
        List list = queryRows(sql, clazz, params);
        return (list == null || list.size()==0) ? null : list.get(0);
    }

    @Override
    public Object queryValue(String sql, Object[] params) {
        return executeQueryTemplate(sql, params, null, new CallBack() {
            @Override
            public Object doExecuteQuery(Connection connection, PreparedStatement ps, ResultSet rs) {
                try {
                    while (rs.next()){
                        return rs.getObject(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    @Override
    public Number queryNumber(String sql, Object[] params) {
        return (Number) queryValue(sql,params);
    }

    @Override
    public Object queryById(Class clazz, Object key) {
        TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();
        String sql = "select * from "+tableInfo.getTname()+" where "+onlyPriKey.getName()+" = ?";
        return queryUniqueRow(sql,clazz,new Object[]{key});
    }

    public abstract List queryPagination(int pageNum, int size);
}
