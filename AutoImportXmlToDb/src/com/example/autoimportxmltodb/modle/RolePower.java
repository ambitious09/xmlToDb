package com.example.autoimportxmltodb.modle;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/***
 * 用户组权限
 */
@DatabaseTable(tableName = "GROUPPOWERS")
public class RolePower implements Serializable
{
	private static final long serialVersionUID = -33490410469885282L;

	public static final String COLUMN_GROUPID = "GROUPID";
	public static final String COLUMN_POWERID = "POWERID";

	/***
	 * 角色标识
	 */
	@DatabaseField(columnName = COLUMN_GROUPID)
	public int RoleId;

	/***
	 * 权限标识
	 * 
	 */
	@DatabaseField(columnName = COLUMN_POWERID)
	public int PowerId;
}