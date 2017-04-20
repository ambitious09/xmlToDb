package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;


/**
 * 颜色分组表
 *
 */

@DatabaseTable(tableName = "ColorGroup")
public class ColorGroup extends BaseDaoEnabled<ColorGroup, Integer>
{
	@DatabaseField
	private int ColorGroupId;

	@DatabaseField
	private String ColorGroupName;
	
	@DatabaseField
	private String CreateDate;
	@DatabaseField
	private String SystemDate;

	/**
	 * 颜色组别编号
	 * @return
	 */
	public int getColorGroupId()
	{
		return ColorGroupId;
	}

	public void setColorGroupId(int id)
	{
		this.ColorGroupId = id;
	}

	/***
	 * 名称
	 * @return
	 */
	public String getColorGroupName()
	{
		return ColorGroupName;
	}

	public void setColorGroupName(String code)
	{
		this.ColorGroupName = code;
	}
	/**
	 * 创建日期
	 * 
	 * @return
	 */
	public Date getCreatedDate() {
		return DataTypeConvert.stringToDate(CreateDate);
	}

	public void setCreatedDate(Date createdDate) {
		CreateDate = DataTypeConvert.dateToString(createdDate);
	}

	/**
	 * 系统日期
	 * 
	 * @return
	 */
	public Date getSystemDate() {
		return DataTypeConvert.stringToDate(SystemDate);
	}

	public void setSystemDate(Date systemDate) {
		SystemDate = DataTypeConvert.dateToString(systemDate);
	}
	
}