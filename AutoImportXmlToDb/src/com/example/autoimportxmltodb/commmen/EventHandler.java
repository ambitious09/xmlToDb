package com.example.autoimportxmltodb.commmen;

/***
 * �¼�������
* Title:EventHandler
* Description:
* Company:
* @date2016-7-23����01:23:52
 */
public abstract class EventHandler<TEventArgs>
{
	/***
	 * �¼�������
	 * 
	 * @param e
	 *            �¼�����
	 */
	public abstract void Deal(TEventArgs e);
}
