package com.example.autoimportxmltodb.modle;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/***
 * �û���Ȩ��
 */
@DatabaseTable(tableName = "GROUPPOWERS")
public class RolePower implements Serializable
{
	private static final long serialVersionUID = -33490410469885282L;

	public static final String COLUMN_GROUPID = "GROUPID";
	public static final String COLUMN_POWERID = "POWERID";

	/***
	 * ��ɫ��ʶ
	 */
	@DatabaseField(columnName = COLUMN_GROUPID)
	public int RoleId;

	/***
	 * Ȩ�ޱ�ʶ
	 * 
	 */
	@DatabaseField(columnName = COLUMN_POWERID)
	public int PowerId;
}