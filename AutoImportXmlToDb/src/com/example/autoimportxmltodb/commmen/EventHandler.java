package com.example.autoimportxmltodb.commmen;

/***
 * 事件处理类
* Title:EventHandler
* Description:
* Company:
* @date2016-7-23下午01:23:52
 */
public abstract class EventHandler<TEventArgs>
{
	/***
	 * 事件处理方法
	 * 
	 * @param e
	 *            事件参数
	 */
	public abstract void Deal(TEventArgs e);
}
