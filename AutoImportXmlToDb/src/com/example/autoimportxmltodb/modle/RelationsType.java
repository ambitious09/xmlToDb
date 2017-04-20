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
 * ��������
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
	 * ���
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
	 * �������
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
	 * ��������
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
	 * ϵͳ����
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