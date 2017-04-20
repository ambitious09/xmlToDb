package com.example.autoimportxmltodb.commmen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import propel.core.functional.Functions.Function1;
import propel.core.functional.Predicates.Predicate1;

/***
 * Linq操作
 * @author liu
 *
 */
public class MyLinq
{
	/***
	 * 判断是否存在满足条件的实体
	 * 
	 * @param values
	 *            实体集合
	 * @param predicate
	 *            判断条件
	 * @return 是否存在
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
	 * Where语句
	 * 
	 * @param values
	 *            集合
	 * @param predicate
	 *            条件
	 * @return 满足条件的集合
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
	 * 查询语句
	 * 
	 * @param values
	 *            数据源
	 * @param predicate
	 *            条件
	 * @param selector
	 *            查询器
	 * @return 查询集合
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
	 * 集合查询语句
	 * 
	 * @param values
	 *            源集合
	 * @param predicate
	 *            查询条件
	 * @param selector
	 *            查询器
	 * @return 查询集合
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
	 * 获取满足条件的第一个实体
	 * 
	 * @param values
	 *            集合
	 * @param predicate
	 *            查询条件
	 * @return 实体
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
	 * 去重复
	 * 
	 * @param values
	 *            操作集合
	 * @param equaltor
	 *            实体比较器
	 * @return 去重复后集合
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
	 * 从集合中查询最大实体
	 * 
	 * @param values
	 *            源集合
	 * @param comparator
	 *            比较器
	 * @return 最大实体
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
	 * 从集合中查询最大实体
	 * 
	 * @param values
	 *            源集合
	 * @param comparator
	 *            比较器
	 * @return 最大实体
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
	 * 求和
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

