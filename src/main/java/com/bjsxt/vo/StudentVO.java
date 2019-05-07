package com.bjsxt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO {
    //SELECT s.id id,s.name `name`,s.age age,t.name teaName from student s JOIN teacher t WHERE s.tid = t.id;
    private Integer id;
    private String name;
    private Integer age;
    private String teaName;
}
