package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 色卡车型表
 * 
 * 
 */

@DatabaseTable(tableName = "ColorAuto")
public class ColorAuto extends BaseDaoEnabled<ColorAuto, Integer>
{
	@DatabaseField
	private int ColorAutoId;

	@DatabaseField
	private int AutoId;

	@DatabaseField
	private int InnerColorId;

	@DatabaseField(columnName = "CreatedDate")
	private String CreatedDate;
	
	@DatabaseField(columnName = "SystemDate")
	private String SystemDate;

	@DatabaseField(columnName = "YearFirstUsed")
	private String YearFirstUsed;
	
	@DatabaseField(columnName = "YearLastUsed")
	private String YearLastUsed;

	/**
	 * 编号
	 * 
	 * @return
	 */
	public int getColorAutoId()
	{
		return ColorAutoId;
	}

	public void setColorAutoId(int id)
	{
		this.ColorAutoId = id;
	}

	/***
	 * 车型编号
	 * 
	 * @return
	 */
	public int getAutoId()
	{
		return AutoId;
	}

	public void setAutoId(int id)
	{
		this.AutoId = id;
	}

	/***
	 * 色卡编号
	 * 
	 * @return
	 */
	public int getInnerColorId()
	{
		return InnerColorId;
	}

	public void setInnerColorId(int id)
	{
		this.InnerColorId = id;
	}

	/**
	 * 创建日期
	 * 
	 * @return
	 */
	public Date getCreateDate()
	{
		return DataTypeConvert.stringToDate(CreatedDate);
	}

	public void setCreatedDate(Date createdDate)
	{
		CreatedDate = DataTypeConvert.dateToString(createdDate);
	}

	/**
	 * 系统日期
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
		YearLastUsed= DataTypeConvert.dateToString(yearLastUsed);
	}
}
