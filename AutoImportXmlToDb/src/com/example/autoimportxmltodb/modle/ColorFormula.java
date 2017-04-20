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
 * ɫ���䷽��
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
	 * ɫ���䷽���
	 * @return
	 */
	public int getColorFormulaId() {
		return ColorFormulaId;
	}

	public void setColorFormulaId(int colorFormulaId) {
		ColorFormulaId = colorFormulaId;
	}
	/**
	 * ɫ�����
	 * @return
	 */
	public int getInnerColorId() {
		return InnerColorId;
	}

	public void setInnerColorId(int innerColorId) {
		InnerColorId = innerColorId;
	}
	/**
	 * �䷽���
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
	 * ����
	 * @return
	 */
	public int getLayerNumber() {
		return LayerNumber;
	}

	public void setLayerNumber(int layerNumber) {
		LayerNumber = layerNumber;
	}
	/**
	 * �䷽����
	 * @return
	 */
	public String getColorFormulaCode() {
		return ColorFormulaCode;
	}

	public void setColorFormulaCode(String colorFormulaCode) {
		ColorFormulaCode = colorFormulaCode;
	}
	/**
	 * ϵͳ����
	 * @return
	 */
	public Date getSystemDate() {
		return SystemDate;
	}

	public void setSystemDate(Date systemDate) {
		SystemDate = systemDate;
	}
	/**
	 * ��������
	 * @return
	 */
	public Date getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}
	/**
	 * ������ID
	 * @return
	 */
	public int getRelationId() {
		return RelationId;
	}

	public void setRelationId(int relationId) {
		RelationId = relationId;
	}

}