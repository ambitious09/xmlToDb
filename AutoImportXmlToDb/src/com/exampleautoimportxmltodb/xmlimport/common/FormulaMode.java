package com.exampleautoimportxmltodb.xmlimport.common;

/***
 * �����䷽ģ��
 */
public enum FormulaMode
{
	/***
	 * ��׼�䷽
	 */
	StandardFormula(0),

	/***
	 * �ͻ��䷽
	 */
	CustomerFormula(1),

	/***
	 * ��ʷ�䷽
	 */
	DispensedFormula(3),

	/***
	 * ��ʷ�䷽����
	 */
	DispensedFormulaReport(4),

	/***
	 * ����ɫ
	 */
	FashionColorCard(8),

	/***
	 * �û�����
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
