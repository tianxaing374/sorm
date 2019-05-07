package com.bjsxt.po;

import java.sql.*;
import java.util.*;

public class Student {

	private String name;
	private Integer id;
	private Integer age;
	private Integer tid;


	public String getName(){
		return name;
	}
	public Integer getId(){
		return id;
	}
	public Integer getAge(){
		return age;
	}
	public Integer getTid(){
		return tid;
	}

	public void setName(String name){this.name = name;}
	public void setId(Integer id){this.id = id;}
	public void setAge(Integer age){this.age = age;}
	public void setTid(Integer tid){this.tid = tid;}

}
