package com.example.autoimportxmltodb.commmen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import propel.core.functional.Functions.Function1;
import propel.core.functional.Predicates.Predicate1;

/***
 * Linq����
 * @author liu
 *
 */
public class MyLinq
{
	/***
	 * �ж��Ƿ��������������ʵ��
	 * 
	 * @param values
	 *            ʵ�弯��
	 * @param predicate
	 *            �ж�����
	 * @return �Ƿ����
	 */
	public static <T> boolean Exist(Iterable<T> values, Predicate1<T> predicate)
	{
		boolean exist = false;

		for (T value : values)
		{
			if (predicate != null && predicate.evaluate(value))
			{
				exist = true;
				break;
			}
		}

		return exist;
	}

	/***
	 * Where���
	 * 
	 * @param values
	 *            ����
	 * @param predicate
	 *            ����
	 * @return ���������ļ���
	 */
	public static <T> List<T> Where(List<T> values, Predicate1<T> predicate)
	{
		List<T> list = new ArrayList<T>();

		for (T value : values)
		{
			if (predicate != null && predicate.evaluate(value))
			{
				list.add(value);
			}
		}

		return list;
	}
	
	/***
	 * ��ѯ���
	 * 
	 * @param values
	 *            ����Դ
	 * @param predicate
	 *            ����
	 * @param selector
	 *            ��ѯ��
	 * @return ��ѯ����
	 */
	public static <T, E> List<E> Select(List<T> values, Predicate1<T> predicate, Function1<T, E> selector)
	{
		List<E> list = new ArrayList<E>();

		for (T value : values)
		{
			if (selector != null && (predicate == null || predicate.apply(value)))
			{
				list.add(selector.apply(value));
			}
		}

		return list;
	}

	/***
	 * ���ϲ�ѯ���
	 * 
	 * @param values
	 *            Դ����
	 * @param predicate
	 *            ��ѯ����
	 * @param selector
	 *            ��ѯ��
	 * @return ��ѯ����
	 */
	public static <T, E> List<E> SelectMany(List<T> values, Predicate1<T> predicate, Function1<T, List<E>> selector)
	{
		List<E> list = new ArrayList<E>();

		for (T value : values)
		{
			if (selector != null && (predicate == null || predicate.apply(value)))
			{
				list.addAll(selector.apply(value));
			}
		}

		return list;
	}

	/***
	 * ��ȡ���������ĵ�һ��ʵ��
	 * 
	 * @param values
	 *            ����
	 * @param predicate
	 *            ��ѯ����
	 * @return ʵ��
	 */
	public static <T> T First(List<T> values, Predicate1<T> predicate)
	{
		T enty = null;

		for (T value : values)
		{
			if (predicate == null || predicate.apply(value))
			{
				enty = value;
				break;
			}
		}

		return enty;
	}

	/***
	 * ȥ�ظ�
	 * 
	 * @param values
	 *            ��������
	 * @param equaltor
	 *            ʵ��Ƚ���
	 * @return ȥ�ظ��󼯺�
	 */
	public static <T> List<T> Distinct(List<T> values, Equaltor<T> equaltor)
	{
		List<T> list = new ArrayList<T>();

		for (T value : values)
		{
			boolean exist = false;
			for (T t : list)
			{
				exist = equaltor != null ? equaltor.Equals(value, t) : value.equals(t);
				if (exist)
				{
					break;
				}
			}
			if (!exist)
			{
				list.add(value);
			}
		}

		return list;
	}

	/***
	 * �Ӽ����в�ѯ���ʵ��
	 * 
	 * @param values
	 *            Դ����
	 * @param comparator
	 *            �Ƚ���
	 * @return ���ʵ��
	 */
	public static <T> T Max(List<T> values, Comparator<T> comparator)
	{
		T t = null;

		if (values.size() > 0)
		{
			t = values.get(0);
			for (T value : values)
			{
				if (comparator != null && comparator.compare(value, t) > 0)
				{
					t = value;
				}
			}
		}

		return t;
	}

	/***
	 * �Ӽ����в�ѯ���ʵ��
	 * 
	 * @param values
	 *            Դ����
	 * @param comparator
	 *            �Ƚ���
	 * @return ���ʵ��
	 */
	public static <T> T Min(List<T> values, Comparator<T> comparator)
	{
		T t = null;

		if (values.size() > 0)
		{
			t = values.get(0);
			for (T value : values)
			{
				if (comparator != null && comparator.compare(value, t) < 0)
				{
					t = value;
				}
			}
		}

		return t;
	}

	/***
	 * ���
	 */
	public static <T> double Sum(List<T> values, Predicate1<T> predicate, Function1<T, Double> sumpram)
	{
		double sumE = 0;

		for (T value : values)
		{
			if (sumpram != null && (predicate == null || predicate.apply(value)))
			{
				sumE += sumpram.apply(value).doubleValue();
			}
		}

		return sumE;
	}
}

