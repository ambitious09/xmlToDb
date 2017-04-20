package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***********************************************************************
 * Module:  ColorType.java
 * Author:  SUQIANG
 * Purpose: Defines the Class ColorType
 ***********************************************************************/
/**
 * ��ɫ����
 * 
 * 
 */
@DatabaseTable(tableName = "ColorType")
public class ColorType extends BaseDaoEnabled<Product, Integer> {
	@DatabaseField
	private int ColorTypeId;

	@DatabaseField
	private String ColorTypeName;

	@DatabaseField
	private Date CreatedDate;

	@DatabaseField
	private Date SystemDate;

	/**
	 * ��ɫ�б���
	 * 
	 * @return
	 */
	public int getColorTypeId() {
		return ColorTypeId;
	}

	public void setColorTypeId(int colorTypeId) {
		ColorTypeId = colorTypeId;
	}

	/**
	 * ����
	 * 
	 * @return
	 */
	public String getColorTypeName() {
		return ColorTypeName;
	}

	public void setColorTypeName(String colorTypeName) {
		ColorTypeName = colorTypeName;
	}

	/**
	 * ����ʱ��
	 * 
	 * @return
	 */

	public Date getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}

	/**
	 * ϵͳʱ��
	 * 
	 * @return
	 */
	public Date getSystemDate() {
		return SystemDate;
	}

	public void setSystemDate(Date systemDate) {
		SystemDate = systemDate;
	}

}