package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;


/**
 * 色卡组别色卡表
 *
 */

@DatabaseTable(tableName = "ColorGroup_Color")
public class ColorGroup_Color extends BaseDaoEnabled<ColorGroup_Color, Integer>
{
	@DatabaseField
	private int ColorGroupColorId;
	

	@DatabaseField
	private int ColorGroupId;
	

	@DatabaseField
	private int ColorId;


	@DatabaseField
	private String CreateDate;
	@DatabaseField
	private String SystemDate;

	/**
	 * 色卡色卡组别编号
	 * @return
	 */
	public int getColorGroupColorId()
	{
		return ColorGroupColorId;
	}

	public void setColorGroupColorId(int id)
	{
		this.ColorGroupColorId = id;
	}

	/***
	 * 色卡组别编号
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
	 * 色卡编号
	 * @return
	 */
	public int getColorId()
	{
		return ColorId;
	}

	public void setColorId(int id)
	{
		this.ColorId = id;
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
