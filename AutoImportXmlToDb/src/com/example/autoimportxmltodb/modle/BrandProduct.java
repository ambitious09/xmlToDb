package com.example.autoimportxmltodb.modle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;


/**
 * 品牌产品表
 * @author liu
 *
 */

@DatabaseTable(tableName = "BrandProduct")
public class BrandProduct extends BaseDaoEnabled<BrandProduct, Integer>
{
	@DatabaseField
	private int BrandProductId;
	

	@DatabaseField
	private int ProductId;
	

	@DatabaseField
	private int BrandId;

	

	/**
	 * 品牌产品编号
	 * @return
	 */
	public int getBrandProductId()
	{
		return BrandProductId;
	}

	public void setBrandProductId(int id)
	{
		this.BrandProductId = id;
	}

	/***
	 * 产品系列编号
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
}
