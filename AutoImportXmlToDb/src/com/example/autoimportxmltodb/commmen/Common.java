package com.example.autoimportxmltodb.commmen;

import java.util.List;
import java.util.UUID;

public class Common {

	/***
	 * �����ַ���
	 * 
	 * @param separator
	 *            �����ַ���
	 * @param objs
	 *            ����Ԫ�ؼ���
	 * @return �ַ���
	 */
	public static String Join(String separator, Object... objs)
	{
		StringBuilder sb = new StringBuilder();

		if (objs == null || objs.length == 0)
		{
			return sb.toString();
		}

		int indexMax = objs.length - 1;
		for (int index = 0; index < indexMax; index++)
		{
			sb.append(objs[index]);
			sb.append(separator);
		}
		sb.append(objs[indexMax]);

		return sb.toString();
	}

	/***
	 * ����Ψһ��ʶ
	 * 
	 * @return Ψһ��ʶ
	 */
	public static String GUID()
	{
		String id = UUID.randomUUID().toString();

		return id.replaceAll("-", "");
	}
	
	/***
	 * �����ַ���
	 * 
	 * @param separator
	 *            �����ַ���
	 * @param objs
	 *            ����Ԫ�ؼ���
	 * @return �ַ���
	 */
	public static String stringJoin(String separator, String... objs)
	{
		StringBuilder sb = new StringBuilder();

		if (objs == null || objs.length == 0)
		{
			return sb.toString();
		}

		int indexMax = objs.length - 1;
		for (int index = 0; index < indexMax; index++)
		{
			sb.append(objs[index]);
			sb.append(separator);
		}
		sb.append(objs[indexMax]);

		return sb.toString();
	}

	/***
	 * �����ַ���
	 * 
	 * @param separator
	 *            �����ַ���
	 * @param objs
	 *            ����Ԫ�ؼ���
	 * @return �ַ���
	 */
	public static String intJoin(String separator, int... objs)
	{
		StringBuilder sb = new StringBuilder();

		if (objs == null || objs.length == 0)
		{
			return sb.toString();
		}

		int indexMax = objs.length - 1;
		for (int index = 0; index < indexMax; index++)
		{
			sb.append(objs[index]);
			sb.append(separator);
		}
		sb.append(objs[indexMax]);

		return sb.toString();
	}
	
	/***
	 * �ַ���Ϊ�ջ���null
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsNullOrEmpty(String str)
	{
		return str == null || str.equals("");
	}
	/***
	 * ����Ϊnull���߳���Ϊ0
	 * 
	 * @param tSet
	 * @return
	 */
	public static <T> boolean IsNullOrEmpty(List<T> tSet)
	{
		return tSet == null || tSet.size() == 0;
	}

	/**
	 * ȫ��ת���
	 * 
	 * @param input
	 *            String.
	 * @return ����ַ���
	 */
	public static String ToDBC(String input)
	{

		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++)
		{
			if (c[i] == '\u3000')
			{
				c[i] = ' ';
			}
			else if (c[i] > '\uFF00' && c[i] < '\uFF5F')
			{
				c[i] = (char) (c[i] - 65248);
			}
		}
		String returnString = new String(c);

		return returnString;
	}

}
