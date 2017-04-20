package com.exampleautoimportxmltodb.xmlimport.common;
import com.exampleautoimportxmltodb.xmlimport.common.ImportProgressingStatus;

/***
 * �䷽��������¼�����
 * 
 * @author �����
 * 
 */
public class FormulaImportProgressingEventArgs
{
	private ImportProgressingStatus _progressingstatus;
	private String _code;
	private String _message;

	/***
	 * �������״̬
	 * 
	 * @return
	 */
	public ImportProgressingStatus getProgressingStatus()
	{
		return _progressingstatus;
	}

	/***
	 * ��Ϣ����
	 */
	public String getCode()
	{
		return _code;
	}

	/***
	 * ��Ϣ
	 * 
	 * @return ��Ϣ
	 */
	public String getMessage()
	{
		return _message;
	}

	/***
	 * ���캯��
	 * 
	 * @param progressing
	 *            ����״̬
	 * @param code
	 *            ������Ϣ
	 */
	public FormulaImportProgressingEventArgs(ImportProgressingStatus progressing, String code)
	{
		_progressingstatus = progressing;
		_code = code;
	}

	/***
	 * ���캯��
	 * 
	 * @param progressing
	 *            ����״̬
	 * @param code
	 *            ������Ϣ����
	 * @param message
	 *            ������Ϣ
	 */
	public FormulaImportProgressingEventArgs(ImportProgressingStatus progressing, String code, String message)
	{
		_progressingstatus = progressing;
		_code = code;
		_message = message;
	}
}
