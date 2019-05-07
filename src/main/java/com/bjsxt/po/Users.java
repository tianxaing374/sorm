package com.bjsxt.po;

import java.sql.*;
import java.util.*;

public class Users {

	private String password;
	private String photo;
	private Integer id;
	private String username;


	public String getPassword(){
		return password;
	}
	public String getPhoto(){
		return photo;
	}
	public Integer getId(){
		return id;
	}
	public String getUsername(){
		return username;
	}

	public void setPassword(String password){this.password = password;}
	public void setPhoto(String photo){this.photo = photo;}
	public void setId(Integer id){this.id = id;}
	public void setUsername(String username){this.username = username;}

}
