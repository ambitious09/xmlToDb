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
 * ɫ���䷽ɫĸ��
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
	 * ɫ���䷽�ɷֱ��
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
	 * ɫ���䷽���
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
	 * ɫĸ���
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
	 * ɫĸ���
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
	 * �ɷְٷֱ�
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
	 * ��������
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
	 * ϵͳ����
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