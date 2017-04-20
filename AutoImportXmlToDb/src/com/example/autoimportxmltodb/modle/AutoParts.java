package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/**
 * ����������
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
	 * ����λ�ñ��
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
	 * ����
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
