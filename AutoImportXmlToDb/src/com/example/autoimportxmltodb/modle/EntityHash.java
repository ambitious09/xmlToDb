package com.example.autoimportxmltodb.modle;

import java.io.Serializable;


/** 
* @ClassName: EntityHash 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 刘会华 
* @date 2016-7-30 下午4:03:04 
*  
*/
 
public class EntityHash implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1183114570718059798L;

	/***
	 * 实体唯一标识
	 */
	public String EntityId;

	/***
	 * 实体对象名
	 */
	public String EntityCategoryName;

	/***
	 * 用户唯一编码
	 */
	public int UserUniqueCode;

	/***
	 * 实体Hash
	 */
	public String EntityHashCode;
}
