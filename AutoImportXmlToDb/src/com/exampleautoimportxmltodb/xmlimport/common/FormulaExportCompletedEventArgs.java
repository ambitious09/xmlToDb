package com.exampleautoimportxmltodb.xmlimport.common;



public class FormulaExportCompletedEventArgs
{
	private CompletedStatus _completedstatus;
	private String _code;

	/***
	 * 导出完成状态
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
	 * 构造函数
	 * 
	 * @param completedStatus
	 *            导出完成状态
	 * @param message
	 *            导出完成信息
	 */
	public FormulaExportCompletedEventArgs(CompletedStatus completedStatus, String message)
	{
		_completedstatus = completedStatus;
		_code = message;
	}
}
