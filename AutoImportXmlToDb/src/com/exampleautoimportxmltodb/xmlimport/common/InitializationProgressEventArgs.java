package com.exampleautoimportxmltodb.xmlimport.common;

/***
 * ��ʼ����չ�¼�����
 * 
 * @author �����
 * 
 */
public class InitializationProgressEventArgs
{
	private String _code;

	/***
	 * ��Ϣ����
	 * 
	 * @return
	 */
	public String getCode()
	{
		return _code;
	}

	/***
	 * ���캯��
	 * 
	 * @param message
	 *            ������Ϣ
	 */
	public InitializationProgressEventArgs(String message)
	{
		_code = message;
	}
}
