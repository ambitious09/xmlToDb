package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***
 * 产品系列表
 * @author 杨栋良
 *
 */

@DatabaseTable(tableName = "Product")
public class Product extends BaseDaoEnabled<Product, Integer>
{
	@DatabaseField
	private int ProductId;

	@DatabaseField
	private String ProductName;
	
	@DatabaseField
	private String ProductCode;
	@DatabaseField
	private String CreateDate;
	@DatabaseField
	private String SystemDate;

	/**
	 * 产品编号
	 * @return
	 */
	public int getProductId()
	{
		return ProductId;
	}

	public void setProductId(int id)
	{
		this.ProductId = id;
	}

	/***
	 * 产品名称
	 * @return
	 */
	public String getProductName()
	{
		return ProductName;
	}

	public void setProductName(String code)
	{
		this.ProductName = code;
	}
	
	/***
	 * 产品编码
	 * @return
	 */
	public String getProductCode()
	{
		return ProductCode;
	}

	public void setProductCode(String name)
	{
		this.ProductCode = name;
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