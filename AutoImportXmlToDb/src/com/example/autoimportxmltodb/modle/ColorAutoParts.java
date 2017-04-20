package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/**
 * ɫ�����Ͳ�����
 *
 */

@DatabaseTable(tableName = "ColorAutoParts")
public class ColorAutoParts extends BaseDaoEnabled<ColorAutoParts, Integer>
{
	@DatabaseField
	private int ColorAutoPartsId;
	

	@DatabaseField
	private int ColorAutoId;
	
	

	@DatabaseField
	private int InnerColorId;
	
	@DatabaseField
	private int AutoPartsId;
	
	@DatabaseField
	private String CreateDate;
	@DatabaseField
	private String SystemDate;


	

	/**
	 * ɫ������λ�ñ��
	 * @return
	 */
	public int getColorAutoPartsId()
	{
		return ColorAutoPartsId;
	}

	public void setColorAutoPartsId(int id)
	{
		this.ColorAutoPartsId = id;
	}

	/***
	 * ɫ�����ͱ��
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
	 * ɫ�����
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
	
	/***
	 * λ�ñ��
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
	 * ��������
	 * 
	 * @return
	 */
	public Date getCreateDate() {
		return DataTypeConvert.stringToDate(CreateDate);
	}

	public void setCreateDate(Date createDate) {
		CreateDate = DataTypeConvert.dateToString(createDate);
	}

	/**
	 * ϵͳ����
	 * 
	 * @return
	 */

	

	public void setSystemDate(Date systemDate) {
		SystemDate = DataTypeConvert.dateToString(systemDate);
	}
	public Date getSystemDate() {
		return DataTypeConvert.stringToDate(SystemDate);
	}
	
}

