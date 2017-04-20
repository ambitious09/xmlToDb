package com.exampleautoimportxmltodb.xmlimport.common;

import java.io.File;

/***
 * 数据推送完成事件参数
 * 
 * 
 */
public class PushDataCompleteEventArgs
{
	private File PushDataFile;

	/***
	 * 推送的数据文件
	 */
	public File getPushDataFile()
	{
		return PushDataFile;
	}

	/***
	 * 构造函数
	 * 
	 * @param pushData
	 *            推送的数据文件
	 */
	public PushDataCompleteEventArgs(File pushData)
	{
		PushDataFile = pushData;
	}
}
