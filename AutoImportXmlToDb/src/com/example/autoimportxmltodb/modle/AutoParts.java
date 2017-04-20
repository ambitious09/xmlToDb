package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 汽车部件表
 * @author liu
 *
 */

@DatabaseTable(tableName = "AutoParts")
public class AutoParts extends BaseDaoEnabled<AutoParts, Integer>
{
	@DatabaseField
	private int AutoPartsId;
	
	@DatabaseField
	private String AutoPartsName;


	@DatabaseField
	private String CreateDate;
	@DatabaseField
	private String SystemDate;

	/**
	 * 工件位置编号
	 * @return
	 */
	public int getAutoPartsId()
	{
		return AutoPartsId;
	}

	public void setAutoPartsId(int id)
	{
		this.AutoPartsId = id;
	}

	/**
	 * 名称
	 */
	public String getAutoPartsName()
	{
		return AutoPartsName;
	}

	public void setAutoPartsName(String code)
	{
		this.AutoPartsName = code;
	}
	/**
	 * 创建日期
	 * 
	 * @return
	 */
	public Date getCreateDate() {
		return DataTypeConvert.stringToDate(CreateDate);
	}

	public void setCreateDate(Date createdDate) {
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
