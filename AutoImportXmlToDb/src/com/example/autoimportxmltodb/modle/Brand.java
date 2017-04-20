package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;


/**
 * 品牌表
 * @author liu
 *
 */

@DatabaseTable(tableName = "Brand")
public class Brand extends BaseDaoEnabled<Brand, Integer>
{
	@DatabaseField
	private int BrandId;

	@DatabaseField
	private String BrandName;
	
	@DatabaseField
	private String CreateDate;
	@DatabaseField
	private String SystemDate;

	/**
	 * 品牌编号
	 * @return
	 */
	public int getBrandId()
	{
		return BrandId;
	}

	public void setBrandId(int id)
	{
		this.BrandId = id;
	}

	/***
	 * 品牌名称
	 * @return
	 */
	public String getBrandName()
	{
		return BrandName;
	}

	public void setBrandName(String code)
	{
		this.BrandName = code;
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