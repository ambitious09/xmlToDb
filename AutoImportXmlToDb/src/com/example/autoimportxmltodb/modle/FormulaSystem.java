package com.example.autoimportxmltodb.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***
 * 配方体系实体
 * 
 * 
 */
@DatabaseTable(tableName = "FORMULASYSTEM")
public class FormulaSystem extends BaseDaoEnabled<FormulaSystem, Integer>
{
	public static final String COLUMN_FORMULASYSTEMID = "FORMULASYSTEMID";
	public static final String COLUMN_FORMULASYSTEMNAME = "FORMULASYSTEMNAME";
	public static final String COLUMN_FORMULASYSTEMVERSION = "FORMULASYSTEMVERSION";
	public static final String COLUMN_CREATETIME = "CREATETIME";

	/***
	 * 配方体系标识
	 */
	@DatabaseField(id = true, columnName = COLUMN_FORMULASYSTEMID)
	private int FormulaSystemId;

	public int getFormulaSystemId()
	{
		return FormulaSystemId;
	}

	public void setFormulaSystemId(int value)
	{
		FormulaSystemId = value;
	}

	/***
	 * 配方体系名称
	 */
	@DatabaseField(columnName = COLUMN_FORMULASYSTEMNAME)
	private String FormulaSystemName;

	public String getFormulaSystemName()
	{
		return FormulaSystemName;
	}

	public void setFormulaSystemName(String value)
	{
		FormulaSystemName = value;
	}

	/***
	 * 配方体系版本
	 */
	@DatabaseField(columnName = COLUMN_FORMULASYSTEMVERSION)
	private String FormulaSystemVersion;

	public String getFormulaSystemVersion()
	{
		return FormulaSystemVersion;
	}

	public void setFormulaSystemVersion(String value)
	{
		FormulaSystemVersion = value;
	}

	/***
	 * 创建时间
	 */
	@DatabaseField(columnName = COLUMN_CREATETIME)
	private String CreateTime;

	public Date getCreateTime()
	{
		return DataTypeConvert.stringToDate(CreateTime);
	}

	public void setCreateTime(Date value)
	{
		CreateTime = DataTypeConvert.dateToString(value);
	}
}
