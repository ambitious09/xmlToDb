package com.exampleautoimportxmltodb.xmlimport.common;

/***
 * 
 *
 * 
 */
public class HasNotReadInfoEventArgs
{
	private int _notreadinfocount;

	/***
	 * 未读消息个数
	 */
	public int getNotReadInfoCount()
	{
		return _notreadinfocount;
	}

	/***
	 * 构造函数
	 * 
	 * @param count
	 *            未读消息个数
	 */
	public HasNotReadInfoEventArgs(int count)
	{
		_notreadinfocount = count;
	}
}
