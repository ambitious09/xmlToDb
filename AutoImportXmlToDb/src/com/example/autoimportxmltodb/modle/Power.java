package com.example.autoimportxmltodb.modle;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

/***
 * Ȩ��ʵ��
 */
/** 
* @ClassName: Power 
* @Description: TODO(������һ�仰��������������) 
* @author ���Ừ 
* @date 2016-7-30 ����4:03:32 
*  
*/
 
public class Power implements Serializable
{
	private static final long serialVersionUID = -791594882940499766L;

	public static final String COLUMN_POWERID = "POWERID";
	public static final String COLUMN_POWERCODE = "POWERCODE";
	public static final String COLUMN_POWERNAME = "POWERNAME";
	public static final String COLUMN_POWERLEVEL = "POWERLEVEL";
	public static final String COLUMN_POWERSTATUS = "POWERSTATUS";
	public static final String COLUMN_REMARK = "REMARK";
	
	/***
	 * Ȩ�ޱ�ʶ
	 * 
	 */
	@DatabaseField(id = true, columnName = COLUMN_POWERID)
	public int PowerId;

	/***
	 * 
	 * Ȩ�ޱ���
	 */
	@DatabaseField(columnName = COLUMN_POWERCODE)
	public String PowerCode;

	/***
	 * 
	 * Ȩ������
	 */
	@DatabaseField(columnName = COLUMN_POWERNAME)
	public String PowerName;

	/***
	 * Ȩ�޼���
	 * 
	 */
	@DatabaseField(columnName = COLUMN_POWERLEVEL)
	public String PowerLevel;

	/***
	 * Ȩ��״̬
	 */
	@DatabaseField(columnName = COLUMN_POWERSTATUS)
	public int PowerStatus;

	/***
	 * 
	 * ��ע
	 */
	@DatabaseField(columnName = COLUMN_REMARK)
	public String Remark;
}
