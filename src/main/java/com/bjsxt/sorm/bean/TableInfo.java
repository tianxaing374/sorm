package com.bjsxt.sorm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 存储表结构的信息
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {
    /**
     * 表名
     * */
    private String tname;

    /**
     * 所有字段的信息
     * */
    private Map<String,ColumnInfo> columns;

    /**
     * 唯一主键（目前只能处理表中有且只有一个主键的情况）
     * */
    private ColumnInfo onlyPriKey;

    /**
     * 如果联合主键，则存在这里
     * */
    private List<ColumnInfo> priKeys;

    public TableInfo(String tname, List<ColumnInfo> priKeys, Map<String, ColumnInfo> columns) {
        this.tname = tname;
        this.columns = columns;
        this.priKeys = priKeys;
    }

    public TableInfo(String tname, Map<String, ColumnInfo> columns, ColumnInfo onlyPriKey) {
        this.tname = tname;
        this.columns = columns;
        this.onlyPriKey = onlyPriKey;
    }
}
