package com.exampleautoimportxmltodb.xmlimport.common;

/***
 * 
 * @author �����
 * 
 */
public class HasNotReadInfoEventArgs
{
	private int _notreadinfocount;

	/***
	 * δ����Ϣ����
	 */
	public int getNotReadInfoCount()
	{
		return _notreadinfocount;
	}

	/***
	 * ���캯��
	 * 
	 * @param count
	 *            δ����Ϣ����
	 */
	public HasNotReadInfoEventArgs(int count)
	{
		_notreadinfocount = count;
	}
}
