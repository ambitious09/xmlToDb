package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***********************************************************************
 * Module:  Relations.java
 * Author:  SUQIANG
 * Purpose: Defines the Class Relations
 ***********************************************************************/
/**
 * 关联表
 * 
 * @author 李孟哲
 * 
 */
@DatabaseTable(tableName = "Relations")
public class Relations extends BaseDaoEnabled<Product, Integer> {
	@DatabaseField
	private int RelationsId;

	@DatabaseField
	private String RelationsName;

	@DatabaseField
	private int RelationsTypeId;

	@DatabaseField
	private String CreatedDate;

	@DatabaseField
	private String SystemDate;

	/**
	 * 关联组别编号
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
	 * 关联编号
	 * 
	 * @return
	 */
	public int getRelationsId() {
		return RelationsId;
	}

	public void setRelationsId(int relationsId) {
		RelationsId = relationsId;
	}

	/**
	 * 关联名称
	 * 
	 * @return
	 */
	public String getRelationsName() {
		return RelationsName;
	}

	public void setRelationsName(String relationsName) {
		RelationsName = relationsName;
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