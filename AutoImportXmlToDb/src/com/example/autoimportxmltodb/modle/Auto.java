package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 汽车表
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
	 * 编号
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
	 * 父编号
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
	 * 名称
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
	 * 关系表编号
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
	 * 创建日期
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
	 * 系统日期
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
	 * 第一次使用时间
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
	 * 最后一次使用时间
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
