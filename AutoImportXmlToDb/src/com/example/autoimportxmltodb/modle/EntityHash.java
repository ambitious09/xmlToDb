package com.example.autoimportxmltodb.modle;

import java.io.Serializable;


/** 
* @ClassName: EntityHash 
* @Description: TODO(������һ�仰��������������) 
* @author ���Ừ 
* @date 2016-7-30 ����4:03:04 
*  
*/
 
public class EntityHash implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1183114570718059798L;

	/***
	 * ʵ��Ψһ��ʶ
	 */
	public String EntityId;

	/***
	 * ʵ�������
	 */
	public String EntityCategoryName;

	/***
	 * �û�Ψһ����
	 */
	public int UserUniqueCode;

	/***
	 * ʵ��Hash
	 */
	public String EntityHashCode;
}
