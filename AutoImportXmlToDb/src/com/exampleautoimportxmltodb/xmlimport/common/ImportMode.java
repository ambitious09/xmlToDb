package com.exampleautoimportxmltodb.xmlimport.common;

/***
 * �䷽���뷽ʽ
 */
public enum ImportMode 
{
	/***
	 * ��յ���
	 */
	EmptyImport(0),

	/***
	 * ���ӵ���
	 */
	AdditionalImport(1);

	private int _value;

	private ImportMode(int value)
	{
		_value = value;
	}

	public int value()
	{
		return _value;
	}

	public static ImportMode valueof(int value)
	{
		ImportMode im = null;

		switch (value)
		{
			case 0:
				im = EmptyImport;
				break;
			case 1:
				im = AdditionalImport;
				break;
			default:
				break;
		}

		return im;
	}
}
