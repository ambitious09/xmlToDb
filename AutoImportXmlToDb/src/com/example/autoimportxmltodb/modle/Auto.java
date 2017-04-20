package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/**
 * ������
 * @author Liu
 *
 */

@DatabaseTable(tableName = "Auto")
public class Auto extends BaseDaoEnabled<Auto, Integer>
{
	@DatabaseField
	private int AutoId;
	

	@DatabaseField
	private int MasterId;
	
	@DatabaseField
	private String AutoName;
	
	@DatabaseField
	private int RelationId;
	@DatabaseField
	private String CreateDate;
	@DatabaseField
	private String SystemDate;
	
	@DatabaseField
	private String YearFirstUsed;
	@DatabaseField
	private String YearLastUsed;


	/**
	 * ���
	 * @return
	 */
	public int getAutoId()
	{
		return AutoId;
	}

	public void setAutoId(int id)
	{
		this.AutoId = id;
	}

	/***
	 * �����
	 * @return
	 */
	public int getMasterId()
	{
		return MasterId;
	}

	public void setMasterId(int id)
	{
		this.MasterId = id;
	}
	/**
	 * ����
	 */
	public String getAutoName()
	{
		return AutoName;
	}

	public void setAutoName(String code)
	{
		this.AutoName = code;
	}
	
	/***
	 * ��ϵ����
	 * @return
	 */
	public int getRelationId()
	{
		return RelationId;
	}

	public void setRelationId(int id)
	{
		this.RelationId = id;
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
	
	/**
	 * ��һ��ʹ��ʱ��
	 * 
	 * @return
	 */
	
	public Date getYearFirstUsed() {
		return DataTypeConvert.stringToDate(YearFirstUsed);
	}


	public void setYearFirstUsed(Date yearFirstUsed) {
		YearFirstUsed = DataTypeConvert.dateToString(yearFirstUsed);
	}

	/**
	 * ���һ��ʹ��ʱ��
	 * 
	 * @return
	 */
	public void setYearLastUsed(Date yearLastUsed) {
		YearLastUsed = DataTypeConvert.dateToString(yearLastUsed);
	}
	public Date getYearLastUsed() {
		return DataTypeConvert.stringToDate(YearLastUsed);
	}
}
