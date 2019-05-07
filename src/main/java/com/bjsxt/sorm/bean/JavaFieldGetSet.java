package com.bjsxt.sorm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JavaFieldGetSet {
    /**
     * 属性的源码信息
     */
    private String fieldInfo;
    /**
     * getter
     */
    private String getInfo;
    /**
     * setter
     */
    private String setInfo;
}
