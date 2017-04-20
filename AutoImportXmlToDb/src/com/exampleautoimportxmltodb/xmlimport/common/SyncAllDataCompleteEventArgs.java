package com.exampleautoimportxmltodb.xmlimport.common;

import java.io.File;
import java.util.List;

/***
 * 所有数据同步完成事件参数
 * 
 * 
 */
public class SyncAllDataCompleteEventArgs
{
	private List<File> SyncDataFiles;

	/***
	 * 同步的所有的数据文件
	 */
	public List<File> getSyncDataFiles()
	{
		return SyncDataFiles;
	}

	/***
	 * 构造函数
	 * 
	 * @param syncDatas
	 *            同步的所有的数据文件
	 */
	public SyncAllDataCompleteEventArgs(List<File> syncDatas)
	{
		SyncDataFiles = syncDatas;
	}
}
