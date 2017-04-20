package com.exampleautoimportxmltodb.xmlimport.common;

import java.io.File;

import com.exampleautoimportxmltodb.xmlimport.common.CompletedStatus;



/***
 * 配方导入完成事件参数
 * 
 * @author 刘红杰
 * 
 */
public class FormulaImportCompletedEventArgs
{
	private CompletedStatus _completedstatus;
	private String _code;
	private File _file;

	/***
	 * 导入完成状态
	 */
	public CompletedStatus getCompletedStatus()
	{
		return _completedstatus;
	}

	/***
	 * 信息代码
	 */
	public String getCode()
	{
		return _code;
	}
	
	/***
	 * 导入文件
	 */
	public File getFile()
	{
		return _file;
	}

	/***
	 * 构造函数
	 * 
	 * @param completedStatus
	 *            导入完成状态
	 * @param message
	 *            导入完成信息
	 */
	public FormulaImportCompletedEventArgs(CompletedStatus completedStatus, String message,File file)
	{
		_completedstatus = completedStatus;
		_code = message;
		_file=file;
	}
}
