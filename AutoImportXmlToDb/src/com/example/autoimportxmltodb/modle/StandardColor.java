package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***********************************************************************
 * Module:  StandardColor.java
 * Author:  SUQIANG
 * Purpose: Defines the Class StandardColor
 ***********************************************************************/

/**
 * 标准色卡
 * 
 * 
 */

@DatabaseTable(tableName = "StandardColor")
public class StandardColor extends BaseDaoEnabled<Product, Integer> {
	@DatabaseField
	private int StandardColorId;

	@DatabaseField
	private int InnerColorId;

	@DatabaseField
	private String StandardColorCode;

	@DatabaseField
	private int StandardColorSystemId;

	@DatabaseField
	private String CreatedDate;

	@DatabaseField
	private String SystemDate;

	/**
	 * 编号
	 * 
	 * @return
	 */

	public int getStandardColorId() {
		return StandardColorId;
	}

	public void setStandardColorId(int standardColorId) {
		StandardColorId = standardColorId;
	}

	/**
	 * 内部色卡编号
	 * 
	 * @return
	 */
	public int getInnerColorId() {
		return InnerColorId;
	}

	public void setInnerColorId(int innerColorId) {
		InnerColorId = innerColorId;
	}

	/**
	 * 色卡编码
	 * 
	 * @return
	 */
	public String getStandardColorCode() {
		return StandardColorCode;
	}

	public void setStandardColorCode(String standardColorCode) {
		StandardColorCode = standardColorCode;
	}

	/**
	 * 标准色卡系统
	 * 
	 * @return
	 */
	public int getStandardColorSystemId() {
		return StandardColorSystemId;
	}

	public void setStandardColorSystemId(int standardColorSystemId) {
		StandardColorSystemId = standardColorSystemId;
	}

	/**
	 * 创建时间
	 * 
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
	 * 
	 * @return
	 */
	public Date getSystemDate() {
		return DataTypeConvert.stringToDate(SystemDate);
	}

	public void setSystemDate(Date systemDate) {
		SystemDate = DataTypeConvert.dateToString(systemDate);
	}

}