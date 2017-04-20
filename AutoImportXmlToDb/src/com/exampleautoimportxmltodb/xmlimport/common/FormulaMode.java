package com.exampleautoimportxmltodb.xmlimport.common;

/***
 * 导入配方模型
 */
public enum FormulaMode
{
	/***
	 * 标准配方
	 */
	StandardFormula(0),

	/***
	 * 客户配方
	 */
	CustomerFormula(1),

	/***
	 * 历史配方
	 */
	DispensedFormula(3),

	/***
	 * 历史配方报表
	 */
	DispensedFormulaReport(4),

	/***
	 * 流行色
	 */
	FashionColorCard(8),

	/***
	 * 用户数据
	 */
	UserData(9);

	private int _value;

	private FormulaMode(int value)
	{
		_value = value;
	}

	public int value()
	{
		return _value;
	}

	public static FormulaMode valueof(int value)
	{
		FormulaMode fm = null;

		switch (value)
		{
			case 0:
				fm = StandardFormula;
				break;
			case 1:
				fm = CustomerFormula;
				break;
			case 3:
				fm = DispensedFormula;
				break;
			case 4:
				fm = DispensedFormulaReport;
				break;
			case 8:
				fm = FashionColorCard;
				break;
			case 9:
				fm = UserData;
				break;
			default:
				break;
		}

		return fm;
	}
}
