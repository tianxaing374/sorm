package com.bjsxt.sorm.utils;

import com.bjsxt.sorm.bean.ColumnInfo;
import com.bjsxt.sorm.bean.JavaFieldGetSet;
import com.bjsxt.sorm.bean.TableInfo;
import com.bjsxt.sorm.core.MySqlTypeConverter;
import com.bjsxt.sorm.core.TableContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by TianXiang on 2019/4/6.
 */
public class JavaFileUtilsTest {

    @Test
    public void test01() {
        ColumnInfo columnInfo = new ColumnInfo("id","int",0);
        JavaFieldGetSet f = JavaFileUtils.createFieldGetSetSRC(columnInfo, new MySqlTypeConverter());
        System.out.println(f.getFieldInfo());
        System.out.println(f.getGetInfo());
        System.out.println(f.getSetInfo());
    }

    @Test
    public void test02() {
        Map<String, TableInfo> map = TableContext.getTableInfos();
        System.out.println(map);
        TableInfo tableInfo = map.get("teacher");
        String javaSrc = JavaFileUtils.createJavaSrc(tableInfo, new MySqlTypeConverter());
        System.out.println(javaSrc);

        JavaFileUtils.createJavaPOFile(tableInfo, new MySqlTypeConverter());

    }

    @Test
    public void test03() {
        Map<String, TableInfo> map = TableContext.getTableInfos();
        for (Map.Entry<String, TableInfo> entry : map.entrySet()) {
            JavaFileUtils.createJavaPOFile(entry.getValue(), new MySqlTypeConverter());
        }
    }

}