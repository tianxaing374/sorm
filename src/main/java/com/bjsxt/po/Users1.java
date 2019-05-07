package com.bjsxt.po;

import java.sql.*;
import java.util.*;

public class Users1 {

	private String password;
	private Integer id;
	private String username;


	public String getPassword(){
		return password;
	}
	public Integer getId(){
		return id;
	}
	public String getUsername(){
		return username;
	}

	public void setPassword(String password){this.password = password;}
	public void setId(Integer id){this.id = id;}
	public void setUsername(String username){this.username = username;}

}
