package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***********************************************************************
 * Module: ColorantPrice.java  Purpose: Defines the Class
 * ColorantPrice
 ***********************************************************************/

@DatabaseTable(tableName = "ColorantPrice")
public class ColorantPrice extends BaseDaoEnabled<ColorantPrice, Integer>
{
	/***
	 * 色母价格编号
	 */
	@DatabaseField(id = true, columnName = "ColorantPriceId")
	public int ColorantPriceId;

	/***
	 * 色母编号
	 */
	@DatabaseField
	public int ColorantId;

	/***
	 * 色母组别编号
	 */
	@DatabaseField
	public int ColorGroupId;

	/***
	 * 色母编码
	 */
	@DatabaseField
	public String ColorantCode;

	/***
	 * 色母名称
	 */
	@DatabaseField
	public String ColorantName;

	/***
	 * 色母描述
	 */
	@DatabaseField
	public String ColorantFeatures;

	/***
	 * 色母密度
	 */
	@DatabaseField
	public double ColorantDensity;

	/***
	 * 规格
	 */
	@DatabaseField
	public double CanSize;

	/***
	 * 单位
	 */
	@DatabaseField
	public String UnitName;

	/***
	 * 色母价格
	 */
	@DatabaseField
	public double ColorantPrice;

	/***
	 * 色母价格系数
	 */
	@DatabaseField
	public double ColorantPriceRate;

	/***
	 * 创建日期
	 */
	@DatabaseField
	public String CreatedDate;

	/***
	 * 系统日期
	 */
	@DatabaseField
	public String SystemDate;

	/**
	 * 获取色母价格编号
	 * 
	 * @return
	 */
	public int getColorantPriceId()
	{
		return ColorantPriceId;
	}

	/***
	 * 设置色母价格编号
	 */
	public void setColorantPriceId(int colorantPriceId)
	{
		ColorantPriceId = colorantPriceId;
	}

	/**
	 * 获取色母编码
	 * 
	 * @return
	 */
	public int getColorantId()
	{
		return ColorantId;
	}

	/***
	 * 设置色母编码
	 */
	public void setColorantId(int colorantId)
	{
		ColorantId = colorantId;
	}

	/**
	 * 颜色组别编号
	 * 
	 * @return
	 */
	public int getColorGroupId()
	{
		return ColorGroupId;
	}

	public void setColorGroupId(int colorGroupId)
	{
		ColorGroupId = colorGroupId;
	}

	/**
	 * 获取色母编码
	 * 
	 * @return
	 */
	public String getColorantCode()
	{
		return ColorantCode;
	}

	/**
	 * 色母名称
	 * 
	 * @return
	 */
	public String getColorantName()
	{
		return ColorantName;
	}

	public void setColorantName(String colorantName)
	{
		ColorantName = colorantName;
	}

	/***
	 * 设置色母编码
	 */
	public void setColorantCode(String colorantCode)
	{
		ColorantCode = colorantCode;
	}

	/**
	 * 特性描述
	 * 
	 * @return
	 */
	public String getColorantFeatures()
	{
		return ColorantFeatures;
	}

	public void setColorantFeatures(String colorantFeatures)
	{
		ColorantFeatures = colorantFeatures;
	}

	/**
	 * 密度
	 * 
	 * @return
	 */
	public double getColorantDensity()
	{
		return ColorantDensity;
	}

	public void setColorantDensity(double colorantDensity)
	{
		ColorantDensity = colorantDensity;
	}

	/**
	 * 获取规格
	 * 
	 * @return
	 */
	public Double getCanSize()
	{
		return CanSize;
	}

	/***
	 * 设置规格
	 */
	public void setCanSize(Double cansize)
	{
		CanSize = cansize;
	}

	/**
	 * 获取单位
	 * 
	 * @return
	 */
	public String getUnitId()
	{
		return UnitName;
	}

	/***
	 * 设置单位
	 */
	public void setUnitId(String unitname)
	{
		UnitName = unitname;
	}

	/**
	 * 获取色母价格
	 * 
	 * @return
	 */
	public Double getColorantPrice()
	{
		return ColorantPrice;
	}

	/***
	 * 设置色母价格
	 */
	public void setColorantPrice(Double colorantPrice)
	{
		ColorantPrice = colorantPrice;
	}

	/**
	 * 获取色母价格系数
	 * 
	 * @return
	 */
	public Double getColorantPriceRate()
	{
		return ColorantPriceRate;
	}

	/***
	 * 设置色母价格系数
	 */
	public void setColorantPriceRate(Double colorantPriceRate)
	{
		ColorantPriceRate = colorantPriceRate;
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
}