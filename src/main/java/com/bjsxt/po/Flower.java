package com.bjsxt.po;

import java.sql.*;
import java.util.*;

public class Flower {

	private String production;
	private Float price;
	private String name;
	private Integer id;


	public String getProduction(){
		return production;
	}
	public Float getPrice(){
		return price;
	}
	public String getName(){
		return name;
	}
	public Integer getId(){
		return id;
	}

	public void setProduction(String production){this.production = production;}
	public void setPrice(Float price){this.price = price;}
	public void setName(String name){this.name = name;}
	public void setId(Integer id){this.id = id;}

}
