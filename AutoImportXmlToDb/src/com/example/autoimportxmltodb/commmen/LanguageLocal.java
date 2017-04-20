package com.example.autoimportxmltodb.commmen;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/***
 * 语言本地化
 * 
 * 
 */
public class LanguageLocal
{
	private static Locale rootLocale = Locale.ROOT;

	private static Locale usingLocale = Locale.getDefault();

	private static Locale enLocale = Locale.US;

	/***
	 * 数字格式化类
	 */
	private static DecimalFormat usingDecimalFormat = new DecimalFormat("################################.################################");

	/***
	 * 获取英文区域
	 */
	public static Locale getEnLocale()
	{
		return enLocale;
	}

	/***
	 * 获取当前区域
	 */
	public static Locale getUsingLocale()
	{
		return usingLocale;
	}

	/***
	 * 设置当前小数点
	 * 
	 * @param dot
	 */
	public static void SetCurrentDot(String dot)
	{
		if (dot.contains("Windows"))
		{
			return;
		}
		if (!dot.equals(""))
		{
			DecimalFormatSymbols symbol = new DecimalFormatSymbols();
			symbol.setDecimalSeparator(dot.charAt(0));

			usingDecimalFormat.setDecimalFormatSymbols(symbol);
		}
	}

	/**
	 * 得到当前小数点的格式
	 * 
	 * @return
	 */
	public static String GetCurrentDot()
	{
		return "" + usingDecimalFormat.getDecimalFormatSymbols().getDecimalSeparator();
	}

	/***
	 * 字符串转换当前配置浮点型
	 * 
	 * @param value
	 * @return
	 */
	public static double ConvertStringToDoubleWithCurent(String value)
	{
		double doubleValue = 0;
		try
		{
			doubleValue = usingDecimalFormat.parse(value).doubleValue();
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return doubleValue;
	}

	/***
	 * 字符串转换当前配置浮点型
	 * 
	 * @param value
	 * @return
	 */
	public static float ConvertStringToFloatWithCurent(String value)
	{
		float floatValue = 0;
		try
		{
			floatValue = usingDecimalFormat.parse(value).floatValue();
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return floatValue;
	}

	/***
	 * 浮点型转换当前配置字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertFloatToStringWithCurent(float value)
	{
		String stringValue = "";

		stringValue = usingDecimalFormat.format(value);

		return stringValue;
	}

	/***
	 * 浮点型转换当前配置字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertDoubleToStringWithCurent(double value)
	{
		return ConvertDoubleToStringWithCurent(value, -1);
	}

	/***
	 * 浮点型转换当前配置字符串
	 * 
	 * @param value
	 * @param bit
	 * @return
	 */
	public static String ConvertDoubleToStringWithCurent(double value, int bit)
	{
		String doubleStr = "";

		if (bit >= 0)
		{
			int maximumFractionDigits = usingDecimalFormat.getMaximumFractionDigits();
			usingDecimalFormat.setMaximumFractionDigits(bit);
			usingDecimalFormat.setRoundingMode(RoundingMode.HALF_UP);
			doubleStr = usingDecimalFormat.format(value);
			usingDecimalFormat.setMaximumFractionDigits(maximumFractionDigits);
		} else
		{
			doubleStr = usingDecimalFormat.format(value);
		}

		return doubleStr;
	}

	/***
	 * 浮点型按指定小数位转换为字符串(英文环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertDoubleToStringWithEn(double value)
	{
		return ConvertDoubleToStringWithEn(value, -1);
	}

	/***
	 * 浮点型按指定小数位转换为字符串(英文环境)
	 * 
	 * @param value
	 * @param bit
	 * @return
	 */
	public static String ConvertDoubleToStringWithEn(double value, int bit)
	{
		NumberFormat nf = NumberFormat.getInstance(enLocale);
		nf.setGroupingUsed(false);

		if (bit >= 0)
		{
			nf.setMaximumFractionDigits(bit);
			nf.setRoundingMode(RoundingMode.HALF_EVEN);
		}

		return nf.format(value);
	}

	/***
	 * 浮点型按指定小数位转换为字符串(当前系统环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertDoubleToStringWithSystem(Double value)
	{
		return ConvertDoubleToStringWithSystem(value, -1);
	}

	/***
	 * 浮点型按指定小数位转换为字符串(当前系统环境)
	 * 
	 * @param value
	 * @param bit
	 * @return
	 */
	public static String ConvertDoubleToStringWithSystem(Double value, int bit)
	{
		NumberFormat nf = NumberFormat.getInstance(rootLocale);

		if (bit >= 0)
		{
			nf.setMaximumFractionDigits(bit);
			nf.setRoundingMode(RoundingMode.HALF_EVEN);
		}

		return nf.format(value);
	}

	/***
	 * 字符串按指定小数位转换为浮点类型(英文环境)
	 * 
	 * @param value
	 * @return
	 */
	public static double ConvertStringToDoubleWithEn(String value)
	{
		return ConvertStringToDoubleWithEn(value, -1);
	}

	/***
	 * 字符串按指定小数位转换为浮点类型(英文环境)
	 * 
	 * @param value
	 * @param bit
	 * @return
	 */
	public static double ConvertStringToDoubleWithEn(String value, int bit)
	{
		NumberFormat nf = NumberFormat.getInstance(enLocale);
		nf.setGroupingUsed(false);

		if (bit >= 0)
		{
			nf.setMaximumFractionDigits(bit);
			nf.setRoundingMode(RoundingMode.HALF_EVEN);
		}

		try
		{
			return nf.parse(value).doubleValue();
		} catch (ParseException e)
		{
			return 0;
		}
	}

	/***
	 * 字符串按指定小数位转换为浮点类型(当前系统环境)
	 * 
	 * @param value
	 * @return
	 */
	public static Double ConvertStringToDoubleWithSystem(String value) throws ParseException
	{
		return ConvertStringToDoubleWithSystem(value, -1);
	}

	/***
	 * 字符串按指定小数位转换为浮点类型(当前系统环境)
	 * 
	 * @param value
	 * @param bit
	 * @return
	 * @throws ParseException
	 */
	public static Double ConvertStringToDoubleWithSystem(String value, int bit) throws ParseException
	{
		NumberFormat nf = NumberFormat.getInstance(rootLocale);

		if (bit >= 0)
		{
			nf.setMaximumFractionDigits(bit);
			nf.setRoundingMode(RoundingMode.HALF_EVEN);
		}

		return nf.parse(value).doubleValue();
	}

	/***
	 * 浮点型按当前货币小数位保留有效位
	 * 
	 * @param value
	 * @return
	 */
	public static Double ConvertDoubleToDoubleWithCurrency(double value)
	{
		return value;
	}

	/***
	 * 浮点型按指定小数位转换为字符串(英文环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertFloatToStringWithEn(float value)
	{
		return ConvertFloatToStringWithEn(value, -1);
	}

	/***
	 * 浮点型按指定小数位转换为字符串(英文环境)
	 * 
	 * @param value
	 * @param bit
	 * @return
	 */
	public static String ConvertFloatToStringWithEn(float value, int bit)
	{
		NumberFormat nf = NumberFormat.getInstance(enLocale);
		nf.setGroupingUsed(false);

		if (bit >= 0)
		{
			nf.setMaximumFractionDigits(bit);
			nf.setRoundingMode(RoundingMode.HALF_EVEN);
		}

		return nf.format(value);
	}

	/***
	 * 浮点型按指定小数位转换为字符串(当前系统环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertFloatToStringWithSystem(float value)
	{
		return ConvertFloatToStringWithSystem(value, -1);
	}

	/***
	 * 浮点型按指定小数位转换为字符串(当前系统环境)
	 * 
	 * @param value
	 * @param bit
	 * @return
	 */
	public static String ConvertFloatToStringWithSystem(float value, int bit)
	{
		NumberFormat nf = NumberFormat.getInstance(rootLocale);

		if (bit >= 0)
		{
			nf.setMaximumFractionDigits(bit);
			nf.setRoundingMode(RoundingMode.HALF_EVEN);
		}

		return nf.format(value);
	}

	/***
	 * 字符串按指定小数位转换为浮点类型(英文环境)
	 * 
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static float ConvertStringToFloatWithEn(String value) throws ParseException
	{
		return ConvertStringToFloatWithEn(value, -1);
	}

	/***
	 * 字符串按指定小数位转换为浮点类型(英文环境)
	 * 
	 * @param value
	 * @param bit
	 * @return
	 * @throws ParseException
	 */
	public static float ConvertStringToFloatWithEn(String value, int bit) throws ParseException
	{
		NumberFormat nf = NumberFormat.getInstance(enLocale);
		nf.setGroupingUsed(false);

		if (bit >= 0)
		{
			nf.setMaximumFractionDigits(bit);
			nf.setRoundingMode(RoundingMode.HALF_EVEN);
		}

		return nf.parse(value).floatValue();
	}

	/***
	 * 字符串按指定小数位转换为日期类型(当前系统环境)
	 * 
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static float ConvertStringToFloatWithSystem(String value) throws ParseException
	{
		return ConvertStringToFloatWithSystem(value, -1);
	}

	/***
	 * 字符串按指定小数位转换为日期类型(当前系统环境)
	 * 
	 * @param value
	 * @param bit
	 * @return
	 * @throws ParseException
	 */
	public static float ConvertStringToFloatWithSystem(String value, int bit) throws ParseException
	{
		NumberFormat nf = NumberFormat.getInstance(rootLocale);

		if (bit >= 0)
		{
			nf.setMaximumFractionDigits(bit);
			nf.setRoundingMode(RoundingMode.HALF_EVEN);
		}

		return nf.parse(value).floatValue();
	}

	/***
	 * 日期转换为长格式字符串(英文环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertDateToLongStringWithEn(Date value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", enLocale);
		return sdf.format(value);

		// SimpleDateFormat ff =SimpleDateFormat.get

		// return value.ToString("D", EnCultureInfo);
	}

	/***
	 * 日期转换为长格式字符串(当前系统环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertDateToLongStringWithSystem(Date value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", rootLocale);
		return sdf.format(value);

		// return value.ToString("D");
	}

	/***
	 * 时间转换为长格式字符串(当前系统环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertTimeToLongStringWithSystem(Date value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", rootLocale);
		return sdf.format(value);
		// return value.ToString("T");
	}

	/***
	 * 日期时间转换为短格式字符串(当前系统环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertTimeToShortStringWithSystem(Date value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", rootLocale);
		return sdf.format(value);
		// return value.ToString("t");
	}

	/***
	 * 时间转换为短格式字符串(英文环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertTimeToShortStringWithEn(Date value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", enLocale);
		return sdf.format(value);

		// return value.ToString("t", EnCultureInfo);
	}

	/***
	 * 时间转换为长格式字符串(英文环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertTimeToLongStringWithEn(Date value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", enLocale);
		return sdf.format(value);

		// return value.ToString("T", EnCultureInfo);
	}

	/***
	 * 日期转换为短格式字符串(英文环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertDateToShortStringWithEn(Date value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", enLocale);
		return sdf.format(value);

		// return value.ToString("d", EnCultureInfo);
	}

	/***
	 * 日期转换为短格式字符串(当前系统环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertDateToShortStringWithSystem(Date value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", rootLocale);
		return sdf.format(value);

		// return value.ToString("d");
	}

	/***
	 * 日期时间转换为长格式字符串(英文环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertDateTimeToLongStringWithEn(Date value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", enLocale);
		return sdf.format(value);
		// return value.ToString("F", EnCultureInfo);
	}

	/***
	 * 日期时间转换为长格式字符串(当前系统环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertDateTimeToLongStringWithSystem(Date value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", rootLocale);
		return sdf.format(value);
		// return value.ToString("F");
	}

	/***
	 * 日期时间转换为短格式字符串(英文环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertDateTimeToShortStringWithEn(Date value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", enLocale);
		return sdf.format(value);

		// return value.ToString("", EnCultureInfo);
	}

	/***
	 * 日期时间转换为短格式字符串(当前系统环境)
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertDateTimeToShortStringWithSystem(Date value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", rootLocale);
		return sdf.format(value);

		// return value.ToString("G");
	}

	/***
	 * 字符串转换为日期类型(英文环境)
	 * 
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static Date ConvertStringToDateTimeWithEn(String value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", enLocale);
		try
		{
			return (Date) (sdf.parseObject(value.toString()));
		} catch (ParseException e)
		{
			try
			{
				SimpleDateFormat sdfnew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", enLocale);

				Date date = sdfnew.parse(value);

				return date;
			} catch (ParseException e1)
			{
				return new Date();
			}
		}
	}

	/***
	 * 字符串转换为日期类型(当前系统环境)
	 * 
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static Date ConvertStringToDateTimeWithSystem(String value) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", rootLocale);
		return sdf.parse(value);

		// return Date.parse(value);
		// return DateTime.Parse(value);
	}

	/***
	 * 浮点型转换当前配置字符串 四舍五入
	 * 
	 * @param value
	 * @param bit
	 * @return
	 */
	public static String ConvertRoundDoubleToStringWithCurent(double value, int bit)
	{
		String doubleStr = "";

		Double ret = null;
		double factor = Math.pow(10, bit);
		ret = Math.floor(value * factor + 0.5) / factor;
		if (bit >= 0)
		{
			int maximumFractionDigits = usingDecimalFormat.getMaximumFractionDigits();
			usingDecimalFormat.setMaximumFractionDigits(bit);
			doubleStr = usingDecimalFormat.format(ret);
			usingDecimalFormat.setMaximumFractionDigits(maximumFractionDigits);
		} else
		{
			doubleStr = usingDecimalFormat.format(ret);
		}

		if (bit > 0)
		{
			String str = "-1";
			str = doubleStr.substring(doubleStr.length() - 1);

			int k = -1;

			if (doubleStr.contains("."))
			{
				String[] list = doubleStr.split("\\.");

				if (list.length > 1)
				{
					k = list[1].length();
				}
			}

			if (Integer.parseInt(str) < 5 && k > 1)
			{
				doubleStr = doubleStr.substring(0, doubleStr.length() - 1) + "5";
			}

			if (Integer.parseInt(str) > 5 && k > 1)
			{
				doubleStr = ConvertRoundDoubleToStringWithCurent(ConvertStringToDoubleWithCurent(doubleStr), 1) + "0";
			}
		}

		return doubleStr;
	}

	/***
	 * 浮点型转换当前配置字符串 四舍五入(按货币流程换算)
	 * 
	 * @param value
	 * @param bit
	 * @return
	 */
	public static Double ConvertCurrencyDoubleToDoubleCurent(double value, int bit)
	{
		Double ret = 0.0;
		double factor = Math.pow(10, bit);
		ret = Math.floor(value * factor + 0.5) / factor;

		if (Double.toString(ret).contains("."))
		{
			int k = -1;

			String[] list = Double.toString(ret).split("\\.");

			k = list[1].length();

			if (k == 2)
			{
				String str = Double.toString(ret).substring(Double.toString(ret).length() - 1);

				if (Integer.parseInt(str) < 5)
				{
					str = Double.toString(ret).substring(0, Double.toString(ret).length() - 1) + "5";

					return ret = Double.parseDouble(str);
				}

				if (Integer.parseInt(str) > 5)
				{
					return ret = ConvertRoundDoubleToDoubleWithCurentT(ret, 1);
				}
			}
		}

		return ret;
	}

	/***
	 * 浮点型转换当前配置字符串 四舍五入
	 * 
	 * @param value
	 * @param bit
	 * @return
	 */
	public static String ConvertRoundDoubleToStringWithCurentT(double value, int bit)
	{
		String doubleStr = "";

		Double ret = null;
		double factor = Math.pow(10, bit);
		ret = Math.floor(value * factor + 0.5) / factor;
		if (bit >= 0)
		{
			int maximumFractionDigits = usingDecimalFormat.getMaximumFractionDigits();
			usingDecimalFormat.setMaximumFractionDigits(bit);
			doubleStr = usingDecimalFormat.format(ret);
			usingDecimalFormat.setMaximumFractionDigits(maximumFractionDigits);
		} else
		{
			doubleStr = usingDecimalFormat.format(ret);
		}

		return doubleStr;
	}

	/***
	 * 浮点型转换当前配置字符串 四舍五入
	 * 
	 * @param value
	 * @param bit
	 * @return
	 */
	public static double ConvertRoundDoubleToDoubleWithCurentT(double value, int bit)
	{
		Double ret = null;
		double factor = Math.pow(10, bit);
		ret = Math.floor(value * factor + 0.5) / factor;

		return ret;
	}

	public static String ConvertDoubleToSplitString(double value, int bit)
	{
		String doubleStr = "" + value;
		if (doubleStr.contains("."))
		{
			String[] list = ("" + value).split("\\.");
			int k = list[1].length();

			if (k > bit)
			{
				doubleStr = list[0] + "." + list[1].substring(0, 2);
			} else
			{
				doubleStr = doubleStr + "0";
			}
		}
		return doubleStr;
	}

	public static String ConvertDoubleToSplitString(String value, int bit)
	{
		String doubleStr = value;
		if (doubleStr.contains("."))
		{
			String[] list = ("" + value).split("\\.");
			int k = list[1].length();

			if (k > bit)
			{
				doubleStr = list[0] + "." + list[1].substring(0, 2);
			} else
			{
				doubleStr = doubleStr + "0";
			}
		}
		return doubleStr;
	}
}
