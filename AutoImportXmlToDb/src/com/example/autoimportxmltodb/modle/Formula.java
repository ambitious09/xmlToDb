package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***********************************************************************
 * Module:  Formula.java
 * Author:  SUQIANG
 * Purpose: Defines the Class Formula
 ***********************************************************************/
/**
 * �䷽��
 *
 */
@DatabaseTable(tableName = "Formula")
public class Formula extends BaseDaoEnabled<Product, Integer> {
	
	@DatabaseField
	private int FormulaId;
	
	@DatabaseField
	private int ProductId;
	
	@DatabaseField
	private int Source;
	@DatabaseField
	private String FormulaVersion;
	
	@DatabaseField
	private String FormulaVersionDate;
	
	@DatabaseField
	private String CreatedDate;
	
	@DatabaseField
	private String SystemDate;
	
	@DatabaseField
	private int RelationId;
	
	/**
	 * �䷽���
	 * @return
	 */
	public int getFormulaId() {
		return FormulaId;
	}

	public void setFormulaId(int formulaId) {
		FormulaId = formulaId;
	}
	/**
	 * ��Ʒϵ��
	 * @return
	 */
	public int getProductId() {
		return ProductId;
	}

	public int getSource()
	{
		return Source;
	}

	public void setSource(int source)
	{
		Source = source;
	}

	public void setProductId(int productId) {
		ProductId = productId;
	}
	/**
	 * �汾��
	 * @return
	 */
	public String getFormulaVersion() {
		return FormulaVersion;
	}

	public void setFormulaVersion(String formulaVersion) {
		FormulaVersion = formulaVersion;
	}
	/**
	 * �䷽����
	 * @return
	 */
	public Date getFormulaVersionDate() {
		return DataTypeConvert.stringToDate(FormulaVersionDate);
	}

	public void setFormulaVersionDate(Date formulaVersionDate) {
		FormulaVersionDate = DataTypeConvert.dateToString(formulaVersionDate);
	}

	/**
	 * ϵͳ����
	 * @return
	 */
	public Date getSystemDate() {
		return DataTypeConvert.stringToDate(SystemDate);
	}

	public void setSystemDate(Date systemDate) {
		SystemDate = DataTypeConvert.dateToString(systemDate);
	}
	/**
	 * ��������
	 * @return
	 */
	public Date getCreatedDate() {
		return DataTypeConvert.stringToDate(CreatedDate);
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = DataTypeConvert.dateToString(createdDate);
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