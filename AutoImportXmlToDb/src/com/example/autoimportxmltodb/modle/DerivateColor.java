package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***********************************************************************
 * Module:  DerivateColor.java
 * Author:  SUQIANG
 * Purpose: Defines the Class DerivateColor
 ***********************************************************************/
/**
 * ����ɫ����
 * 
 * 
 */
@DatabaseTable(tableName = "DerivateColor")
public class DerivateColor extends BaseDaoEnabled<Product, Integer>
{
	@DatabaseField
	private int DerivateColorId;

	@DatabaseField
	private int InnerParentColorId;

	@DatabaseField
	private int InnerColorId;
	
	@DatabaseField
	private int RelationsId;

	@DatabaseField
	private String CreatedDate;

	@DatabaseField
	private String SystemDate;

	/**
	 * ƫ��ɫ�����
	 * 
	 * @return
	 */
	public int getDerivateColorId()
	{
		return DerivateColorId;
	}

	public void setDerivateColorId(int derivateColorId)
	{
		DerivateColorId = derivateColorId;
	}

	/**
	 * ��׼ɫ�����
	 * 
	 * @return
	 */
	public int getInnerParentColorId()
	{
		return InnerParentColorId;
	}

	public void setInnerParentColorId(int innerParentColorId)
	{
		InnerParentColorId = innerParentColorId;
	}

	/**
	 * �ڲ�ɫ�����
	 * 
	 * @return
	 */
	public int getInnerColorId()
	{
		return InnerColorId;
	}

	public void setInnerColorId(int innerColorId)
	{
		InnerColorId = innerColorId;
	}
	
	/**
	 * ��ϵ��ID������ɫ����ID��
	 * 
	 * @return
	 */
	public int getRelationsId()
	{
		return RelationsId;
	}

	public void setRelationsId(int relationsid)
	{
		RelationsId = relationsid;
	}

	/**
	 * ����ʱ��
	 * 
	 * @return
	 */
	public Date getCreatedDate()
	{
		return DataTypeConvert.stringToDate(CreatedDate);
	}

	public void setCreatedDate(Date createdDate)
	{
		CreatedDate = DataTypeConvert.dateToString(createdDate);
	}

	/**
	 * ϵͳʱ��
	 * 
	 * @return
	 */
	public Date getSystemDate()
	{
		return DataTypeConvert.stringToDate(SystemDate);
	}

	public void setSystemDate(Date systemDate)
	{
		SystemDate = DataTypeConvert.dateToString(systemDate);
	}

}