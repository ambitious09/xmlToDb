package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***********************************************************************
 * Module:  FormulaRemark.java
 * Author:  SUQIANG
 * Purpose: Defines the Class FormulaRemark
 ***********************************************************************/

/**
 * 配方信息表
 * 
 * 
 */
@DatabaseTable(tableName = "FormulaRemark")
public class FormulaRemark extends BaseDaoEnabled<Product, Integer> {

	@DatabaseField
	private int FormulaRemarkId;

	@DatabaseField
	private String FormulaRemarks;

	@DatabaseField
	private Date CreatedDate;

	@DatabaseField
	private Date SystemDate;

	@DatabaseField
	private int ColorFormulaId;

	/**
	 * 编号
	 * 
	 * @return
	 */
	public int getFormulaRemarkId() {
		return FormulaRemarkId;
	}

	public void setFormulaRemarkId(int formulaRemarkId) {
		FormulaRemarkId = formulaRemarkId;
	}

	/**
	 * 描述
	 * 
	 * @return
	 */
	public String getFormulaRemarks() {
		return FormulaRemarks;
	}

	public void setFormulaRemarks(String formulaRemarks) {
		FormulaRemarks = formulaRemarks;
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

	/**
	 * 色卡配方编号
	 * 
	 * @return
	 */
	public int getColorFormulaId() {
		return ColorFormulaId;
	}

	public void setColorFormulaId(int colorFormulaId) {
		ColorFormulaId = colorFormulaId;
	}


}