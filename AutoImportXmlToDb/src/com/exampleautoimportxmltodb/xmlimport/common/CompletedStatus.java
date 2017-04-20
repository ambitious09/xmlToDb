package com.exampleautoimportxmltodb.xmlimport.common;

/***
 * ���״̬
 */
public enum CompletedStatus 
{
	/***
	 * �ɹ�
	 */
	Success(1),

	/***
	 * ʧ��
	 */
	Failure(2),

	/***
	 * ��Ҫȷ��
	 */
	NeedConfirm(3),

	/***
	 * ��Ҫѡ��
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
