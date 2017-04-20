package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;


/**
 * Ʒ�Ʊ�
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
	 * Ʒ�Ʊ��
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
	 * Ʒ������
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
	 * ��������
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
	 * ϵͳ����
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