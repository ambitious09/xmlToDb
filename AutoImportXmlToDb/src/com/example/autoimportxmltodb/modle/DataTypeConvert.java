package com.example.autoimportxmltodb.modle;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/***
 * ����ת��������
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
	 * Sqlite���ڸ�ʽ
	 */
	private static final String Sqlite_DateFormat = "yyyy-MM-dd HH:mm:ss";

	/***
	 * ��ת��date����Ϊ�����ַ���
	 * 
	 * @param value
	 *            date����
	 * @return �����ַ���
	 */
	public static String dateToString(Date value)
	{
		String dateString = "";

		dateString = dateToString(value, Sqlite_DateFormat);

		return dateString;
	}

	/***
	 * ��ת��date����Ϊ�����ַ���
	 * 
	 * @param value
	 *            date����
	 * @param formatstr
	 *            ��ʽģ��
	 * @return �����ַ���
	 */
	public static String dateToString(Date value, String formatstr)
	{
		SimpleDateFormat format = new SimpleDateFormat(formatstr);
		String dateString = "";

		dateString = format.format(value);

		return dateString;
	}

	/***
	 * �������ַ���ת��Ϊdate����
	 * 
	 * @param value
	 *            �����ַ���
	 * @return date����
	 */
	public static Date stringToDate(String value)
	{
		Date date = null;

		date = stringToDate(value, Sqlite_DateFormat);

		return date;
	}

	/***
	 * �������ַ���ת��Ϊdate����
	 * 
	 * @param value
	 *            �����ַ���
	 * @param formatstr
	 *            ��ʽģ��
	 * @return date����
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
	 * ʱ���������
	 * 
	 * @param date
	 *            ʱ��
	 * @param count
	 *            �������
	 * @return ʱ��
	 */
	public static Date AddDate(Date date, int count)
	{
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);
		gc.add(Calendar.DATE, count);

		return gc.getTime();
	}

	/***
	 * ʱ���������
	 * 
	 * @param date
	 *            ʱ��
	 * @param count
	 *            �������
	 * @return ʱ��
	 */
	public static Date AddMonth(Date date, int count)
	{
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);
		gc.add(Calendar.MONTH, count);

		return gc.getTime();
	}

	/***
	 * ʱ���������
	 * 
	 * @param date
	 *            ʱ��
	 * @param count
	 *            �������
	 * @return ʱ��
	 */
	public static Date AddYear(Date date, int count)
	{
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);
		gc.add(Calendar.YEAR, count);

		return gc.getTime();
	}
}