package com.exampleautoimportxmltodb.xmlimport.common;

/***
 * 配方类别
 */
public enum FormulaType
{
	/***
	 * 全部类型
	 */
	All(0),

	/***
	 * 标准配方
	 */
	StandardFormula(1),

	/***
	 * 客户配方
	 */
	CustomerFormula(2),

	/***
	 * 手动配方
	 */
	ManualFormula(3),

	/***
	 * 历史配方
	 */
	HistoryFormula(4),

	/***
	 * Top配方
	 */
	TopFormula(20),

	/***
	 * 流行色配方
	 */
	FashionFormula(21),

	/***
	 * 常用色配方
	 */
	CommonlyFormula(22);

	private int _value;

	private FormulaType(int value)
	{
		_value = value;
	}

	public int value()
	{
		return _value;
	}

	public static FormulaType valueof(int value)
	{
		FormulaType fm = null;

		switch (value)
		{
			case 0:
				fm = All;
				break;
			case 1:
				fm = StandardFormula;
				break;
			case 2:
				fm = CustomerFormula;
				break;
			case 3:
				fm = ManualFormula;
				break;
			case 4:
				fm = HistoryFormula;
				break;
			case 20:
				fm = TopFormula;
				break;
			case 21:
				fm = FashionFormula;
				break;
			case 22:
				fm = CommonlyFormula;
				break;
			default:
				break;
		}

		return fm;
	}
}
