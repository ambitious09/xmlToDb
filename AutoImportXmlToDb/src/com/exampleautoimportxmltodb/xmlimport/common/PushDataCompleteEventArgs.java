package com.exampleautoimportxmltodb.xmlimport.common;

import java.io.File;

/***
 * ������������¼�����
 * 
 * 
 */
public class PushDataCompleteEventArgs
{
	private File PushDataFile;

	/***
	 * ���͵������ļ�
	 */
	public File getPushDataFile()
	{
		return PushDataFile;
	}

	/***
	 * ���캯��
	 * 
	 * @param pushData
	 *            ���͵������ļ�
	 */
	public PushDataCompleteEventArgs(File pushData)
	{
		PushDataFile = pushData;
	}
}
