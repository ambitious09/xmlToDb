package com.example.autoimportxmltodb.modle;

import java.io.Serializable;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/***
 * 用户实体
 */
@DatabaseTable(tableName = "MANAGER")
public class User implements Serializable
{
	private static final long serialVersionUID = -9039415022676018174L;

	public static final String COLUMN_MANAGERID = "MANAGERID";
	public static final String COLUMN_MANAGERNAME = "MANAGERNAME";
	public static final String COLUMN_PASSWORD = "PASSWORD";
	public static final String COLUMN_MANAGERSTATUS = "MANAGERSTATUS";
	public static final String COLUMN_REMARK = "REMARK";

	/***
	 * 用户标识
	 */
	@DatabaseField(id = true, columnName = COLUMN_MANAGERID)
	public int UserId;

	/***
	 * 
	 * 用户名
	 */
	@DatabaseField(columnName = COLUMN_MANAGERNAME)
	public String UserName;

	/***
	 * 
	 * 密码
	 */
	@DatabaseField(columnName = COLUMN_PASSWORD)
	public String PassWord;

	/***
	 * 
	 * 权限列表
	 */
	public List<Power> Powers;

	/***
	 * 角色列表
	 */
	public List<Role> Roles;

	/***
	 * 用户状态
	 */
	@DatabaseField(columnName = COLUMN_MANAGERSTATUS)
	public int UserStatus;

	/***
	 * 是否允许删除
	 */
	public boolean DisableDelete = true;

	/***
	 * 备注
	 */
	@DatabaseField(columnName = COLUMN_REMARK)
	public String Remark;
}
