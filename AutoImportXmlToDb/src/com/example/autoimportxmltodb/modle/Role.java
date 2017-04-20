package com.example.autoimportxmltodb.modle;

import java.io.Serializable;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/***
 * 角色实体
 */
@DatabaseTable(tableName = "POWERGROUP")
public class Role implements Serializable
{
	private static final long serialVersionUID = -2515430923890259055L;

	public static final String COLUMN_POWERGROUPID = "POWERGROUPID";
	public static final String COLUMN_GROUPNAME = "GROUPNAME";
	public static final String COLUMN_GROUPLEVEL = "GROUPLEVEL";
	public static final String COLUMN_REMARK = "REMARK";

	/***
	 * 角色标识
	 */
	@DatabaseField(id = true, columnName = COLUMN_POWERGROUPID)
	public int RoleId;

	/***
	 * 角色名称
	 */
	@DatabaseField(columnName = COLUMN_GROUPNAME)
	public String RoleName;

	/***
	 * 角色级别
	 */
	@DatabaseField(columnName = COLUMN_GROUPLEVEL)
	public int RoleLevel;

	/***
	 * 是否允许删除
	 */
	public boolean DisableDelete = true;

	/***
	 * 权限列表
	 */
	public List<Power> PowerModels;

	/***
	 * 备注
	 */
	@DatabaseField(columnName = COLUMN_REMARK)
	public String Remark;
}
