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
	 * ɫĸ�۸���
	 */
	@DatabaseField(id = true, columnName = "ColorantPriceId")
	public int ColorantPriceId;

	/***
	 * ɫĸ���
	 */
	@DatabaseField
	public int ColorantId;

	/***
	 * ɫĸ�����
	 */
	@DatabaseField
	public int ColorGroupId;

	/***
	 * ɫĸ����
	 */
	@DatabaseField
	public String ColorantCode;

	/***
	 * ɫĸ����
	 */
	@DatabaseField
	public String ColorantName;

	/***
	 * ɫĸ����
	 */
	@DatabaseField
	public String ColorantFeatures;

	/***
	 * ɫĸ�ܶ�
	 */
	@DatabaseField
	public double ColorantDensity;

	/***
	 * ���
	 */
	@DatabaseField
	public double CanSize;

	/***
	 * ��λ
	 */
	@DatabaseField
	public String UnitName;

	/***
	 * ɫĸ�۸�
	 */
	@DatabaseField
	public double ColorantPrice;

	/***
	 * ɫĸ�۸�ϵ��
	 */
	@DatabaseField
	public double ColorantPriceRate;

	/***
	 * ��������
	 */
	@DatabaseField
	public String CreatedDate;

	/***
	 * ϵͳ����
	 */
	@DatabaseField
	public String SystemDate;

	/**
	 * ��ȡɫĸ�۸���
	 * 
	 * @return
	 */
	public int getColorantPriceId()
	{
		return ColorantPriceId;
	}

	/***
	 * ����ɫĸ�۸���
	 */
	public void setColorantPriceId(int colorantPriceId)
	{
		ColorantPriceId = colorantPriceId;
	}

	/**
	 * ��ȡɫĸ����
	 * 
	 * @return
	 */
	public int getColorantId()
	{
		return ColorantId;
	}

	/***
	 * ����ɫĸ����
	 */
	public void setColorantId(int colorantId)
	{
		ColorantId = colorantId;
	}

	/**
	 * ��ɫ�����
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
	 * ��ȡɫĸ����
	 * 
	 * @return
	 */
	public String getColorantCode()
	{
		return ColorantCode;
	}

	/**
	 * ɫĸ����
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
	 * ����ɫĸ����
	 */
	public void setColorantCode(String colorantCode)
	{
		ColorantCode = colorantCode;
	}

	/**
	 * ��������
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
	 * �ܶ�
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
	 * ��ȡ���
	 * 
	 * @return
	 */
	public Double getCanSize()
	{
		return CanSize;
	}

	/***
	 * ���ù��
	 */
	public void setCanSize(Double cansize)
	{
		CanSize = cansize;
	}

	/**
	 * ��ȡ��λ
	 * 
	 * @return
	 */
	public String getUnitId()
	{
		return UnitName;
	}

	/***
	 * ���õ�λ
	 */
	public void setUnitId(String unitname)
	{
		UnitName = unitname;
	}

	/**
	 * ��ȡɫĸ�۸�
	 * 
	 * @return
	 */
	public Double getColorantPrice()
	{
		return ColorantPrice;
	}

	/***
	 * ����ɫĸ�۸�
	 */
	public void setColorantPrice(Double colorantPrice)
	{
		ColorantPrice = colorantPrice;
	}

	/**
	 * ��ȡɫĸ�۸�ϵ��
	 * 
	 * @return
	 */
	public Double getColorantPriceRate()
	{
		return ColorantPriceRate;
	}

	/***
	 * ����ɫĸ�۸�ϵ��
	 */
	public void setColorantPriceRate(Double colorantPriceRate)
	{
		ColorantPriceRate = colorantPriceRate;
	}

	/**
	 * ����ʱ��
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
	 * ϵͳʱ��
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