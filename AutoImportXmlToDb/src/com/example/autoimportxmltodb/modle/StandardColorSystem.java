package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***********************************************************************
 * Module:  StandardColorSystem.java
 * Author:  SUQIANG
 * Purpose: Defines the Class StandardColorSystem
 ***********************************************************************/

/**
 * 标准色卡系统
 * 
 * 
 */

@DatabaseTable(tableName = "StandardColorSystem")
public class StandardColorSystem extends BaseDaoEnabled<Product, Integer>{
	@DatabaseField
	private int StandardColorSystemId;
	
	
	@DatabaseField
	private String StandardColorSystemName;
	
	@DatabaseField
	private String CreatedDate;
	
	@DatabaseField
	private String SystemDate;
	
	/**
	 * 编号
	 * @return
	 */
	public int getStandardColorSystemId() {
		return StandardColorSystemId;
	}

	public void setStandardColorSystemId(int standardColorSystemId) {
		StandardColorSystemId = standardColorSystemId;
	}
	/**
	 * 色卡系统
	 * @return
	 */
	public String getStandardColorSystemName() {
		return StandardColorSystemName;
	}

	public void setStandardColorSystemName(String standardColorSystemName) {
		StandardColorSystemName = standardColorSystemName;
	}
	/**
	 * 创建时间
	 * @return
	 */
	public Date getCreatedDate() {
		return DataTypeConvert.stringToDate(CreatedDate);
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = DataTypeConvert.dateToString(createdDate);
	}
	/**
	 * 系统时间
	 * @return
	 */
	public Date getSystemDate() {
		return DataTypeConvert.stringToDate(SystemDate);
	}

	public void setSystemDate(Date systemDate) {
		SystemDate = DataTypeConvert.dateToString(systemDate);
	}
	
	

}