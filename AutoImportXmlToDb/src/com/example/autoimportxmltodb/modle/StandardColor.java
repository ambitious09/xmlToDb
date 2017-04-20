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
 * ��׼ɫ��
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
	 * ���
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
	 * �ڲ�ɫ�����
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
	 * ɫ������
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
	 * ��׼ɫ��ϵͳ
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
	 * ����ʱ��
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
	 * ϵͳʱ��
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