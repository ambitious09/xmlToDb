package com.exampleautoimportxmltodb.xmlimport.common;

import java.io.File;
import java.util.List;

/***
 * ��������ͬ������¼�����
 * 
 * 
 */
public class SyncAllDataCompleteEventArgs
{
	private List<File> SyncDataFiles;

	/***
	 * ͬ�������е������ļ�
	 */
	public List<File> getSyncDataFiles()
	{
		return SyncDataFiles;
	}

	/***
	 * ���캯��
	 * 
	 * @param syncDatas
	 *            ͬ�������е������ļ�
	 */
	public SyncAllDataCompleteEventArgs(List<File> syncDatas)
	{
		SyncDataFiles = syncDatas;
	}
}
