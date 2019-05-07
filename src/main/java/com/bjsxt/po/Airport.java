package com.bjsxt.po;

import java.sql.*;
import java.util.*;

public class Airport {

	private String cityName;
	private Integer id;
	private String portName;


	public String getCityName(){
		return cityName;
	}
	public Integer getId(){
		return id;
	}
	public String getPortName(){
		return portName;
	}

	public void setCityName(String cityName){this.cityName = cityName;}
	public void setId(Integer id){this.id = id;}
	public void setPortName(String portName){this.portName = portName;}

}
