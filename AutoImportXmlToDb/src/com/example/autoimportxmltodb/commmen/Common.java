package com.example.autoimportxmltodb.commmen;

import java.util.List;
import java.util.UUID;

public class Common {

	/***
	 * 链接字符串
	 * 
	 * @param separator
	 *            连接字符串
	 * @param objs
	 *            链接元素集合
	 * @return 字符串
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
	 * 生成唯一标识
	 * 
	 * @return 唯一标识
	 */
	public static String GUID()
	{
		String id = UUID.randomUUID().toString();

		return id.replaceAll("-", "");
	}
	
	/***
	 * 链接字符串
	 * 
	 * @param separator
	 *            连接字符串
	 * @param objs
	 *            链接元素集合
	 * @return 字符串
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
	 * 链接字符串
	 * 
	 * @param separator
	 *            连接字符串
	 * @param objs
	 *            链接元素集合
	 * @return 字符串
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
	 * 字符串为空或者null
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsNullOrEmpty(String str)
	{
		return str == null || str.equals("");
	}
	/***
	 * 集合为null或者长度为0
	 * 
	 * @param tSet
	 * @return
	 */
	public static <T> boolean IsNullOrEmpty(List<T> tSet)
	{
		return tSet == null || tSet.size() == 0;
	}

	/**
	 * 全角转半角
	 * 
	 * @param input
	 *            String.
	 * @return 半角字符串
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
