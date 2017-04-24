package com.exampleautoimportxmltodb.xmlimport.common;
import com.exampleautoimportxmltodb.xmlimport.common.ImportProgressingStatus;

/***
 * 配方导入进度事件参数
 * 
 * 
 */
public class FormulaImportProgressingEventArgs
{
	private ImportProgressingStatus _progressingstatus;
	private String _code;
	private String _message;

	/***
	 * 导入进度状态
	 * 
	 * @return
	 */
	public ImportProgressingStatus getProgressingStatus()
	{
		return _progressingstatus;
	}

	/***
	 * 信息代码
	 */
	public String getCode()
	{
		return _code;
	}

	/***
	 * 信息
	 * 
	 * @return 信息
	 */
	public String getMessage()
	{
		return _message;
	}

	/***
	 * 构造函数
	 * 
	 * @param progressing
	 *            进度状态
	 * @param code
	 *            进度信息
	 */
	public FormulaImportProgressingEventArgs(ImportProgressingStatus progressing, String code)
	{
		_progressingstatus = progressing;
		_code = code;
	}

	/***
	 * 构造函数
	 * 
	 * @param progressing
	 *            进度状态
	 * @param code
	 *            进度信息代码
	 * @param message
	 *            进度信息
	 */
	public FormulaImportProgressingEventArgs(ImportProgressingStatus progressing, String code, String message)
	{
		_progressingstatus = progressing;
		_code = code;
		_message = message;
	}
}
