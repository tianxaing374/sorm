package com.bjsxt.po;

import java.sql.*;
import java.util.*;

public class Log {

	private String accIn;
	private Double money;
	private String accOut;
	private Integer id;


	public String getAccIn(){
		return accIn;
	}
	public Double getMoney(){
		return money;
	}
	public String getAccOut(){
		return accOut;
	}
	public Integer getId(){
		return id;
	}

	public void setAccIn(String accIn){this.accIn = accIn;}
	public void setMoney(Double money){this.money = money;}
	public void setAccOut(String accOut){this.accOut = accOut;}
	public void setId(Integer id){this.id = id;}

}
