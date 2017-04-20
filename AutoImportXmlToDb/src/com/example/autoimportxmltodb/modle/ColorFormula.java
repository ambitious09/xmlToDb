package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***********************************************************************
 * Module:  ColorFormula.java
 * Author:  SUQIANG
 * Purpose: Defines the Class ColorFormula
 ***********************************************************************/
/**
 * 色卡配方表
 *
 */
@DatabaseTable(tableName = "ColorFormula")
public class ColorFormula extends BaseDaoEnabled<Product, Integer> {
	@DatabaseField
	private int ColorFormulaId;
	
	@DatabaseField
	private int InnerColorId;
	
	@DatabaseField
	private int FormulaId;
	
	@DatabaseField
	private int LayerNumber;
	
	@DatabaseField
	private String ColorFormulaCode;
	@DatabaseField
	private String LayerDescription;
	
	@DatabaseField
	private Date SystemDate;
	

	@DatabaseField
	private Date CreatedDate;
	
	@DatabaseField
	private int RelationId;
	/**
	 * 色卡配方编号
	 * @return
	 */
	public int getColorFormulaId() {
		return ColorFormulaId;
	}

	public void setColorFormulaId(int colorFormulaId) {
		ColorFormulaId = colorFormulaId;
	}
	/**
	 * 色卡编号
	 * @return
	 */
	public int getInnerColorId() {
		return InnerColorId;
	}

	public void setInnerColorId(int innerColorId) {
		InnerColorId = innerColorId;
	}
	/**
	 * 配方编号
	 * @return
	 */
	public int getFormulaId() {
		return FormulaId;
	}

	public String getLayerDescription()
	{
		return LayerDescription;
	}

	public void setLayerDescription(String layerDescription)
	{
		LayerDescription = layerDescription;
	}

	public void setFormulaId(int formulaId) {
		FormulaId = formulaId;
	}
	/**
	 * 层数
	 * @return
	 */
	public int getLayerNumber() {
		return LayerNumber;
	}

	public void setLayerNumber(int layerNumber) {
		LayerNumber = layerNumber;
	}
	/**
	 * 配方编码
	 * @return
	 */
	public String getColorFormulaCode() {
		return ColorFormulaCode;
	}

	public void setColorFormulaCode(String colorFormulaCode) {
		ColorFormulaCode = colorFormulaCode;
	}
	/**
	 * 系统日期
	 * @return
	 */
	public Date getSystemDate() {
		return SystemDate;
	}

	public void setSystemDate(Date systemDate) {
		SystemDate = systemDate;
	}
	/**
	 * 创建日期
	 * @return
	 */
	public Date getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}
	/**
	 * 关联表ID
	 * @return
	 */
	public int getRelationId() {
		return RelationId;
	}

	public void setRelationId(int relationId) {
		RelationId = relationId;
	}

}