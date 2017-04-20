package com.example.autoimportxmltodb.modle;

/***
 * 价格策略
 */
public enum PricingStrategy 
{
	/***
	 * 厂商定价优先
	 */
	CompanyFirst(1),

	/***
	 * 店铺定价优先
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
