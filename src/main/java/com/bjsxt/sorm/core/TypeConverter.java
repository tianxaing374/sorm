package com.bjsxt.sorm.core;

/**
 * 负责java数据类型和数据库类型的相互转换
 */
public interface TypeConverter {

    /**
     * 数据库类型到java类型
     * @param columnType 数据库类型
     * @return java数据类型
     * */
    String databaseType2JavaType(String columnType);

    /**
     * java类型到数据库类型
     * @param javaDataType java数据类型
     * @return 数据库类型
     * */
    String javaType2DatabaseType(String javaDataType);

}
