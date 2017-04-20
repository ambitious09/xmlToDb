package com.exampleautoimportxmltodb.xmlimport.common;

/***
 * 导入数据文件版本
 */
public enum ImportFileVersion
{
	/***
	 * 版本3.1
	 */
    ColorExpert3_1(31),

	/***
	 * 版本3
	 */
	ColorExpert3(3),

	/***
	 * 版本2
	 */
	ColorExpert2(2);

	private int _value;

	private ImportFileVersion(int value)
	{
		_value = value;
	}

	public int value()
	{
		return _value;
	}

	public static ImportFileVersion valueof(int value)
	{
		ImportFileVersion ifv = null;

		switch (value)
		{
			case 2:
				ifv = ColorExpert2;
				break;
			case 3:
				ifv = ColorExpert3;
				break;
			case 31:
				ifv = ColorExpert3_1;
				break;
			default:
				break;
		}

		return ifv;
	}
}
