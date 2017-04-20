package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***********************************************************************
 * Module:  ColorFormulaColorants.java
 * Author:  SUQIANG
 * Purpose: Defines the Class ColorFormulaColorants
 ***********************************************************************/

/**
 * 色卡配方色母表
 * 
 * 
 */

@DatabaseTable(tableName = "ColorFormulaColorants")
public class ColorFormulaColorants extends BaseDaoEnabled<Product, Integer> {
	@DatabaseField
	private int ColorFormulaColorantsId;

	@DatabaseField
	private int ColorFormulaId;

	@DatabaseField
	private int ColorantsId;

	@DatabaseField
	private int ColorantSequence;

	@DatabaseField
	private String WeightPercent;

	@DatabaseField
	private Date CreatedDate;

	@DatabaseField
	private Date SystemDate;

	/**
	 * 色卡配方成分编号
	 * 
	 * @return
	 */
	public int getColorFormulaColorantsId() {
		return ColorFormulaColorantsId;
	}

	public void setColorFormulaColorantsId(int colorFormulaColorantsId) {
		ColorFormulaColorantsId = colorFormulaColorantsId;
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

	/**
	 * 色母编号
	 * 
	 * @return
	 */
	public int getColorantsId() {
		return ColorantsId;
	}

	public void setColorantsId(int colorantsId) {
		ColorantsId = colorantsId;
	}

	/**
	 * 色母序号
	 * 
	 * @return
	 */
	public int getColorantSequence() {
		return ColorantSequence;
	}

	public void setColorantSequence(int colorantSequence) {
		ColorantSequence = colorantSequence;
	}

	/**
	 * 成分百分比
	 * 
	 * @return
	 */
	public String getWeightPercent() {
		return WeightPercent;
	}

	public void setWeightPercent(String weightPercent) {
		WeightPercent = weightPercent;
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