package com.exampleautoimportxmltodb.xmlimport.common;

/***
 * 完成状态
 */
public enum CompletedStatus 
{
	/***
	 * 成功
	 */
	Success(1),

	/***
	 * 失败
	 */
	Failure(2),

	/***
	 * 需要确认
	 */
	NeedConfirm(3),

	/***
	 * 需要选择
	 */
	NeedChoose(4);

	private int _value;

	private CompletedStatus(int value)
	{
		_value = value;
	}

	public int value()
	{
		return _value;
	}
}
