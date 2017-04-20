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
 * ������
 * 
 * @author ������
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
	 * ���������
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
	public int getRelationsId() {
		return RelationsId;
	}

	public void setRelationsId(int relationsId) {
		RelationsId = relationsId;
	}

	/**
	 * ��������
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