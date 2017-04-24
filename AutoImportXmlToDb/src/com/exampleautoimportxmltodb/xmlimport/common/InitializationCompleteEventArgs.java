package com.exampleautoimportxmltodb.xmlimport.common;


/***
 * 初始化完成
 * 
 *
 * 
 */
public class InitializationCompleteEventArgs
{
	private CompletedStatus _completedstatus;

	/***
	 * 完成状态
	 */
	public CompletedStatus geetCompletedStatus()
	{
		return _completedstatus;
	}

	private String _code;

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
	 *            完成状态
	 * @param message
	 *            完成信息
	 */
	public InitializationCompleteEventArgs(CompletedStatus completedStatus, String message)
	{
		_completedstatus = completedStatus;
		_code = message;
	}
}
