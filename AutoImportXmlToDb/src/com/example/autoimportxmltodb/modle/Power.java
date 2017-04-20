package com.example.autoimportxmltodb.modle;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

/***
 * 权限实体
 */
/** 
* @ClassName: Power 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 刘会华 
* @date 2016-7-30 下午4:03:32 
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
	 * 权限标识
	 * 
	 */
	@DatabaseField(id = true, columnName = COLUMN_POWERID)
	public int PowerId;

	/***
	 * 
	 * 权限编码
	 */
	@DatabaseField(columnName = COLUMN_POWERCODE)
	public String PowerCode;

	/***
	 * 
	 * 权限名称
	 */
	@DatabaseField(columnName = COLUMN_POWERNAME)
	public String PowerName;

	/***
	 * 权限级别
	 * 
	 */
	@DatabaseField(columnName = COLUMN_POWERLEVEL)
	public String PowerLevel;

	/***
	 * 权限状态
	 */
	@DatabaseField(columnName = COLUMN_POWERSTATUS)
	public int PowerStatus;

	/***
	 * 
	 * 备注
	 */
	@DatabaseField(columnName = COLUMN_REMARK)
	public String Remark;
}
