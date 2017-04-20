package com.example.autoimportxmltodb.modle;

import java.io.Serializable;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/***
 * �û�ʵ��
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
	 * �û���ʶ
	 */
	@DatabaseField(id = true, columnName = COLUMN_MANAGERID)
	public int UserId;

	/***
	 * 
	 * �û���
	 */
	@DatabaseField(columnName = COLUMN_MANAGERNAME)
	public String UserName;

	/***
	 * 
	 * ����
	 */
	@DatabaseField(columnName = COLUMN_PASSWORD)
	public String PassWord;

	/***
	 * 
	 * Ȩ���б�
	 */
	public List<Power> Powers;

	/***
	 * ��ɫ�б�
	 */
	public List<Role> Roles;

	/***
	 * �û�״̬
	 */
	@DatabaseField(columnName = COLUMN_MANAGERSTATUS)
	public int UserStatus;

	/***
	 * �Ƿ�����ɾ��
	 */
	public boolean DisableDelete = true;

	/***
	 * ��ע
	 */
	@DatabaseField(columnName = COLUMN_REMARK)
	public String Remark;
}
