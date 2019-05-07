package com.bjsxt.sorm.core;

import java.util.List;

//设计分页的时候需要针对mysql做特殊处理
public class MySqlQuery extends AbstractQuery {

    @Override
    public List queryPagination(int pageNum, int size) {
        return null;
    }
}
