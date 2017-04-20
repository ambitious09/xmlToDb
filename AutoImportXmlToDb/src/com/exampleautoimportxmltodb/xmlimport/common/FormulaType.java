package com.exampleautoimportxmltodb.xmlimport.common;

/***
 * �䷽���
 */
public enum FormulaType
{
	/***
	 * ȫ������
	 */
	All(0),

	/***
	 * ��׼�䷽
	 */
	StandardFormula(1),

	/***
	 * �ͻ��䷽
	 */
	CustomerFormula(2),

	/***
	 * �ֶ��䷽
	 */
	ManualFormula(3),

	/***
	 * ��ʷ�䷽
	 */
	HistoryFormula(4),

	/***
	 * Top�䷽
	 */
	TopFormula(20),

	/***
	 * ����ɫ�䷽
	 */
	FashionFormula(21),

	/***
	 * ����ɫ�䷽
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
