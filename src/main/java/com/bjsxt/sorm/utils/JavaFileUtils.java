package com.bjsxt.sorm.utils;

import com.bjsxt.sorm.bean.ColumnInfo;
import com.bjsxt.sorm.bean.JavaFieldGetSet;
import com.bjsxt.sorm.bean.TableInfo;
import com.bjsxt.sorm.core.DBManager;
import com.bjsxt.sorm.core.TypeConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 封装了java文件（源代码）常用操作
 * */
public class JavaFileUtils {

    /**
     * 根据字段信息生成java属性信息，一个setter/getter方法源码
     * @param column
     * @param converter
     * @return java属性信息，一个setter/getter方法源码
     */
    public static JavaFieldGetSet createFieldGetSetSRC(ColumnInfo column, TypeConverter converter){
        JavaFieldGetSet jfgs = new JavaFieldGetSet();
        String javaFieldType = converter.databaseType2JavaType(column.getDataType());
        jfgs.setFieldInfo("\tprivate "+javaFieldType+" "+column.getName()+";\n");

        /*
        * public String getUsername(){return username;}
        * */
        StringBuilder getSrc = new StringBuilder();
        getSrc.append("\tpublic ")
                .append(javaFieldType)
                .append(" get")
                .append(StringUtils.firstChar2UpperCase(column.getName()))
                .append("(){\n")
                .append("\t\treturn ")
                .append(column.getName())
                .append(";\n\t}\n");
        jfgs.setGetInfo(getSrc.toString());

        /**
         * public void setUsername(String username){this.username = username;}
         */
        StringBuilder setSrc = new StringBuilder();
        setSrc.append("\tpublic void set")
                .append(StringUtils.firstChar2UpperCase(column.getName()))
                .append("(")
                .append(javaFieldType)
                .append(" ")
                .append(column.getName())
                .append("){this.")
                .append(column.getName())
                .append(" = ")
                .append(column.getName())
                .append(";}\n");
        jfgs.setSetInfo(setSrc.toString());
        return jfgs;
    }

    public static String createJavaSrc(TableInfo tableInfo,TypeConverter converter){
        StringBuilder src = new StringBuilder();

        Map<String, ColumnInfo> columns = tableInfo.getColumns();
        List<JavaFieldGetSet> javaFields = new ArrayList<>();

        for (ColumnInfo columnInfo : columns.values()) {
            javaFields.add(createFieldGetSetSRC(columnInfo,converter));
        }

        //生成package
        src.append("package ").append(DBManager.getConf().getPoPackage()).append(";\n\n");

        //生成import
        src.append("import java.sql.*;\n");
        src.append("import java.util.*;\n\n");

        //生成类声明语句
        src.append("public class ").append(StringUtils.firstChar2UpperCase(tableInfo.getTname())).append(" {\n\n");

        //生成属性类别
        for (JavaFieldGetSet javaField : javaFields) {
            src.append(javaField.getFieldInfo());
        }
        src.append("\n\n");
        //生成get/set
        for (JavaFieldGetSet javaField : javaFields) {
            src.append(javaField.getGetInfo());
        }
        src.append("\n");
        for (JavaFieldGetSet javaField : javaFields) {
            src.append(javaField.getSetInfo());
        }
        src.append("\n");
        //类结束
        src.append("}\n");
        return src.toString();
    }

    public static void createJavaPOFile(TableInfo tableInfo,TypeConverter converter){
        String src = createJavaSrc(tableInfo, converter);

        String srcPath = DBManager.getConf().getSrcPath()+"\\";
        String packagePath = DBManager.getConf().getPoPackage().replaceAll("\\.","\\\\");

        File f = new File(srcPath+packagePath);
        if(f.exists()){
            f.delete();
        }
        f.mkdirs();
        BufferedWriter bw = null;

        File javaFile = new File(f.getAbsoluteFile()+"/"+StringUtils.firstChar2UpperCase(tableInfo.getTname()+".java"));
        try {
            bw = new BufferedWriter(new FileWriter(javaFile));
            bw.write(src);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw!=null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
