package com.exampleautoimportxmltodb.xmlimport.common;

/***
 * 初始化进展事件参数
 * 
 * @author 刘红杰
 * 
 */
public class InitializationProgressEventArgs
{
	private String _code;

	/***
	 * 信息代码
	 * 
	 * @return
	 */
	public String getCode()
	{
		return _code;
	}

	/***
	 * 构造函数
	 * 
	 * @param message
	 *            进度信息
	 */
	public InitializationProgressEventArgs(String message)
	{
		_code = message;
	}
}
