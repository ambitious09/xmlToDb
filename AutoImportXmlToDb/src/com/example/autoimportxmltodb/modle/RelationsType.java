package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/**********************************************************************
 * Module:  RelationsType.java
 * Author:  SUQIANG
 * Purpose: Defines the Class RelationsType
 ***********************************************************************/

/***
 * 关联类别表
 * 
 * 
 */
@DatabaseTable(tableName = "RelationsType")
public class RelationsType extends BaseDaoEnabled<Product, Integer> {
	@DatabaseField
	private int RelationsTypeId;

	@DatabaseField
	private String RelationsTypeName;

	@DatabaseField
	private String CreatedDate;

	@DatabaseField
	private String SystemDate;

	/**
	 * 编号
	 * 
	 * @return
	 */
	public int getRelationsTypeId() {
		return RelationsTypeId;
	}

	public void setRelationsTypeId(int relationsTypeId) {
		RelationsTypeId = relationsTypeId;
	}

	/**
	 * 类别名称
	 * 
	 * @return
	 */
	public String getRelationsTypeName() {
		return RelationsTypeName;
	}

	public void setRelationsTypeName(String relationsTypeName) {
		RelationsTypeName = relationsTypeName;
	}

	/**
	 * 创建日期
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
	 * 系统日期
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