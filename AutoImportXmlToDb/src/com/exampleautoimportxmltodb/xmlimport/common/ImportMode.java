package com.exampleautoimportxmltodb.xmlimport.common;

/***
 * 配方导入方式
 */
public enum ImportMode 
{
	/***
	 * 清空导入
	 */
	EmptyImport(0),

	/***
	 * 附加导入
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
