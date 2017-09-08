package com.exampleautoimportxmltodb.xmlimport.common;


/***
 * ��ʼ�����
 * 
 * @author ������
 * 
 */
public class InitializationCompleteEventArgs
{
	private CompletedStatus _completedstatus;

	/***
	 * ���״̬
	 */
	public CompletedStatus geetCompletedStatus()
	{
		return _completedstatus;
	}

	private String _code;

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
	 *            ���״̬
	 * @param message
	 *            �����Ϣ
	 */
	public InitializationCompleteEventArgs(CompletedStatus completedStatus, String message)
	{
		_completedstatus = completedStatus;
		_code = message;
	}
}
