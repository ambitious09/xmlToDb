package com.exampleautoimportxmltodb.xmlimport.common;



public class FormulaExportCompletedEventArgs
{
	private CompletedStatus _completedstatus;
	private String _code;

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
	 * ���캯��
	 * 
	 * @param completedStatus
	 *            �������״̬
	 * @param message
	 *            ���������Ϣ
	 */
	public FormulaExportCompletedEventArgs(CompletedStatus completedStatus, String message)
	{
		_completedstatus = completedStatus;
		_code = message;
	}
}
