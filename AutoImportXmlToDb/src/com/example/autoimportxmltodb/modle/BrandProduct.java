package com.example.autoimportxmltodb.modle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;


/**
 * Ʒ�Ʋ�Ʒ��
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
	 * Ʒ�Ʋ�Ʒ���
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
	 * ��Ʒϵ�б��
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
}
