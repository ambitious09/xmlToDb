package com.example.autoimportxmltodb.modle;

import java.io.Serializable;

/***
 * �����û�����ʵ��
 */

/** 
* @ClassName: LocalPasswordModel 
* @Description: TODO(������һ�仰��������������) 
* @author ���Ừ 
* @date 2016-7-30 ����4:03:25 
*  
*/
 
public class LocalPasswordModel implements Serializable
{
	private static final long serialVersionUID = -9148279118491102365L;
	
	/***
	 * ��ǰ����
	 */
	public String OldPassword;

	/***
	 * ������
	 */
	public String NewPassword;

	/***
	 * ȷ��������
	 */
	public String ConfirmPassword;
}
