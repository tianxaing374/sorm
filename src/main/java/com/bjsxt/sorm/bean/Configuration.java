package com.bjsxt.sorm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理配置信息
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {
    //驱动类
    private String driver;
    //jdbc的url
    private String url;
    private String user;
    private String pwd;
    private String usingDB;
    //项目的源码路径
    private String srcPath;
    //扫描生成java类的包（po:Persistence object持久化对象）
    private String poPackage;
    private String queryClass;
    private int poolMinSize;
    private int poolMaxSize;
}
