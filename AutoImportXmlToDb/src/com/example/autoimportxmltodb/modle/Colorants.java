package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***********************************************************************
 * Module:  Colorants.java
 * Author:  SUQIANG
 * Purpose: Defines the Class Colorants
 ***********************************************************************/

/**
 * 色母表
 * 
 * 
 */
@DatabaseTable(tableName = "Colorants")
public class Colorants extends BaseDaoEnabled<Colorants, Integer> {
	@DatabaseField
	private int ColorantId;

	@DatabaseField
	private int ColorGroupId;

	@DatabaseField
	private String ColorantCode;

	@DatabaseField
	private String ColorantName;

	@DatabaseField
	private String ColorantFeatures;

	@DatabaseField
	private double ColorantDensity;

	@DatabaseField
	private Date CreatedDate;

	@DatabaseField
	private Date SystemDate;

	/**
	 * 色母编号
	 * 
	 * @return
	 */
	public int getColorantId() {
		return ColorantId;
	}

	public void setColorantId(int colorantId) {
		ColorantId = colorantId;
	}

	/**
	 * 颜色组别编号
	 * 
	 * @return
	 */
	public int getColorGroupId() {
		return ColorGroupId;
	}

	public void setColorGroupId(int colorGroupId) {
		ColorGroupId = colorGroupId;
	}

	/**
	 * 色母编码
	 * 
	 * @return
	 */
	public String getColorantCode() {
		return ColorantCode;
	}

	public void setColorantCode(String colorantCode) {
		ColorantCode = colorantCode;
	}

	/**
	 * 色母名称
	 * 
	 * @return
	 */
	public String getColorantName() {
		return ColorantName;
	}

	public void setColorantName(String colorantName) {
		ColorantName = colorantName;
	}

	/**
	 * 特性描述
	 * 
	 * @return
	 */
	public String getColorantFeatures() {
		return ColorantFeatures;
	}

	public void setColorantFeatures(String colorantFeatures) {
		ColorantFeatures = colorantFeatures;
	}

	/**
	 * 密度
	 * 
	 * @return
	 */
	public double getColorantDensity() {
		return ColorantDensity;
	}

	public void setColorantDensity(double colorantDensity) {
		ColorantDensity = colorantDensity;
	}

	/**
	 * 创建日期
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
	 * 系统日期
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