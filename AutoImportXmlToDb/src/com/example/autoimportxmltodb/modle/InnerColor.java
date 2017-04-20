package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***********************************************************************
 * Module:  InnerColor.java
 * Author:  SUQIANG
 * Purpose: Defines the Class InnerColor
 ***********************************************************************/

/**
 * 内部色卡表
 * 
 * 
 */
@DatabaseTable(tableName = "InnerColor")
public class InnerColor extends BaseDaoEnabled<Product, Integer>
{
	@DatabaseField
	private int InnerColorId;

	@DatabaseField
	private int ColorTypeId;

	@DatabaseField
	private String ColorCode;

	@DatabaseField
	private String ColorName;

	@DatabaseField
	private String YearFirstUsed;

	@DatabaseField
	private String YearLastUsed;

	@DatabaseField
	private String RGB;

	@DatabaseField
	private String CreatedDate;

	@DatabaseField
	private String SystemDate;

	@DatabaseField
	private int RelationId;

	/**
	 * 编号
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
	 * 颜色类别编号
	 * 
	 * @return
	 */
	public int getColorTypeId()
	{
		return ColorTypeId;
	}

	public void setColorTypeId(int colorTypeId)
	{
		ColorTypeId = colorTypeId;
	}

	/**
	 * 颜色编码
	 * 
	 * @return
	 */
	public String getColorCode()
	{
		return ColorCode;
	}

	public void setColorCode(String colorCode)
	{
		ColorCode = colorCode;
	}

	/**
	 * 颜色名称
	 * 
	 * @return
	 */
	public String getColorName()
	{
		return ColorName;
	}

	public void setColorName(String colorName)
	{
		ColorName = colorName;
	}

	/**
	 * 开始时间
	 * 
	 * @return
	 */
	public Date getYearFirstUsed()
	{
		return DataTypeConvert.stringToDate(YearFirstUsed);
	}

	public void setYearFirstUsed(Date yearFirstUsed)
	{
		YearFirstUsed = DataTypeConvert.dateToString(yearFirstUsed);
	}

	/**
	 * 结束时间
	 * 
	 * @return
	 */
	public Date getYearLastUsed()
	{
		return DataTypeConvert.stringToDate(YearLastUsed);
	}

	public void setYearLastUsed(Date yearLastUsed)
	{
		YearLastUsed = DataTypeConvert.dateToString(yearLastUsed);
	}

	/**
	 * RGB
	 * 
	 * @return
	 */
	public String getRGB()
	{
		return RGB;
	}

	public void setRGB(String rGB)
	{
		RGB = rGB;
	}

	/**
	 * 创建时间
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
	 * 系统时间
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

	/**
	 * 关联表ID
	 * 
	 * @return
	 */
	public int getRelationId()
	{
		return RelationId;
	}

	public void setRelationId(int relationId)
	{
		RelationId = relationId;
	}

}