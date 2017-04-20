package com.example.autoimportxmltodb.modle;

import java.io.Serializable;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/***
 * ��ɫʵ��
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
	 * ��ɫ��ʶ
	 */
	@DatabaseField(id = true, columnName = COLUMN_POWERGROUPID)
	public int RoleId;

	/***
	 * ��ɫ����
	 */
	@DatabaseField(columnName = COLUMN_GROUPNAME)
	public String RoleName;

	/***
	 * ��ɫ����
	 */
	@DatabaseField(columnName = COLUMN_GROUPLEVEL)
	public int RoleLevel;

	/***
	 * �Ƿ�����ɾ��
	 */
	public boolean DisableDelete = true;

	/***
	 * Ȩ���б�
	 */
	public List<Power> PowerModels;

	/***
	 * ��ע
	 */
	@DatabaseField(columnName = COLUMN_REMARK)
	public String Remark;
}
