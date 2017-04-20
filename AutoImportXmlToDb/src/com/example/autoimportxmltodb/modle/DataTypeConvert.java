package com.example.autoimportxmltodb.modle;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/***
 * 类型转换公共类
 * 
 * 
 */
public class DataTypeConvert implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8285598282938642943L;

	/**
	 * Sqlite日期格式
	 */
	private static final String Sqlite_DateFormat = "yyyy-MM-dd HH:mm:ss";

	/***
	 * 将转换date类型为日期字符串
	 * 
	 * @param value
	 *            date类型
	 * @return 日期字符串
	 */
	public static String dateToString(Date value)
	{
		String dateString = "";

		dateString = dateToString(value, Sqlite_DateFormat);

		return dateString;
	}

	/***
	 * 将转换date类型为日期字符串
	 * 
	 * @param value
	 *            date类型
	 * @param formatstr
	 *            格式模板
	 * @return 日期字符串
	 */
	public static String dateToString(Date value, String formatstr)
	{
		SimpleDateFormat format = new SimpleDateFormat(formatstr);
		String dateString = "";

		dateString = format.format(value);

		return dateString;
	}

	/***
	 * 将日期字符串转换为date类型
	 * 
	 * @param value
	 *            日期字符串
	 * @return date类型
	 */
	public static Date stringToDate(String value)
	{
		Date date = null;

		date = stringToDate(value, Sqlite_DateFormat);

		return date;
	}

	/***
	 * 将日期字符串转换为date类型
	 * 
	 * @param value
	 *            日期字符串
	 * @param formatstr
	 *            格式模板
	 * @return date类型
	 */
	public static Date stringToDate(String value, String formatstr)
	{
		SimpleDateFormat format = new SimpleDateFormat(formatstr);
		Date date = new Date();

		try
		{
			date = format.parse(value.replace('T', ' '));
		}
		catch (ParseException e)
		{
			if(value.contains("/"))
			{
				format=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				try 
				{
					date = format.parse(value.replace('T', ' '));
				} catch (Exception e2) {}
			}
		}

		return date;
	}

	/***
	 * 时间天数相加
	 * 
	 * @param date
	 *            时间
	 * @param count
	 *            相加天数
	 * @return 时间
	 */
	public static Date AddDate(Date date, int count)
	{
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);
		gc.add(Calendar.DATE, count);

		return gc.getTime();
	}

	/***
	 * 时间月数相加
	 * 
	 * @param date
	 *            时间
	 * @param count
	 *            相加月数
	 * @return 时间
	 */
	public static Date AddMonth(Date date, int count)
	{
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);
		gc.add(Calendar.MONTH, count);

		return gc.getTime();
	}

	/***
	 * 时间年数相加
	 * 
	 * @param date
	 *            时间
	 * @param count
	 *            相加年数
	 * @return 时间
	 */
	public static Date AddYear(Date date, int count)
	{
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);
		gc.add(Calendar.YEAR, count);

		return gc.getTime();
	}
}