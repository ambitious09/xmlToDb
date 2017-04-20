package com.example.autoimportxmltodb.modle;

import java.io.Serializable;

/***
 * 更改用户密码实体
 */

/** 
* @ClassName: LocalPasswordModel 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 刘会华 
* @date 2016-7-30 下午4:03:25 
*  
*/
 
public class LocalPasswordModel implements Serializable
{
	private static final long serialVersionUID = -9148279118491102365L;
	
	/***
	 * 当前密码
	 */
	public String OldPassword;

	/***
	 * 新密码
	 */
	public String NewPassword;

	/***
	 * 确认新密码
	 */
	public String ConfirmPassword;
}
