package com.bjsxt.po;

import java.sql.*;
import java.util.*;

public class Account {

	private String PASSWORD;
	private Double balance;
	private String name;
	private Integer id;
	private String account;


	public String getPASSWORD(){
		return PASSWORD;
	}
	public Double getBalance(){
		return balance;
	}
	public String getName(){
		return name;
	}
	public Integer getId(){
		return id;
	}
	public String getAccount(){
		return account;
	}

	public void setPASSWORD(String PASSWORD){this.PASSWORD = PASSWORD;}
	public void setBalance(Double balance){this.balance = balance;}
	public void setName(String name){this.name = name;}
	public void setId(Integer id){this.id = id;}
	public void setAccount(String account){this.account = account;}

}
