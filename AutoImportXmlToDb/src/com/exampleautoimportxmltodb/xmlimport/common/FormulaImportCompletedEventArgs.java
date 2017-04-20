package com.exampleautoimportxmltodb.xmlimport.common;

import java.io.File;

import com.exampleautoimportxmltodb.xmlimport.common.CompletedStatus;



/***
 * �䷽��������¼�����
 * 
 * @author �����
 * 
 */
public class FormulaImportCompletedEventArgs
{
	private CompletedStatus _completedstatus;
	private String _code;
	private File _file;

	/***
	 * �������״̬
	 */
	public CompletedStatus getCompletedStatus()
	{
		return _completedstatus;
	}

	/***
	 * ��Ϣ����
	 */
	public String getCode()
	{
		return _code;
	}
	
	/***
	 * �����ļ�
	 */
	public File getFile()
	{
		return _file;
	}

	/***
	 * ���캯��
	 * 
	 * @param completedStatus
	 *            �������״̬
	 * @param message
	 *            ���������Ϣ
	 */
	public FormulaImportCompletedEventArgs(CompletedStatus completedStatus, String message,File file)
	{
		_completedstatus = completedStatus;
		_code = message;
		_file=file;
	}
}
