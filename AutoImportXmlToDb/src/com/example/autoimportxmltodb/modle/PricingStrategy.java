package com.example.autoimportxmltodb.modle;

/***
 * �۸����
 */
public enum PricingStrategy 
{
	/***
	 * ���̶�������
	 */
	CompanyFirst(1),

	/***
	 * ���̶�������
	 */
	ShopFirst(2);

	private int _value;

	private PricingStrategy(int value)
	{
		_value = value;
	}

	public int value()
	{
		return _value;
	}

	public static PricingStrategy valueof(int value)
	{
		PricingStrategy fm = null;

		switch (value)
		{
			case 1:
				fm = CompanyFirst;
				break;
			case 2:
				fm = ShopFirst;
				break;
			default:
				break;
		}

		return fm;
	}
}
