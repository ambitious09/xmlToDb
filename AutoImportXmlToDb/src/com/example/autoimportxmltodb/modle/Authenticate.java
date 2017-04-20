package com.example.autoimportxmltodb.modle;

import java.io.Serializable;

import android.content.Context;


/***
 * 认证实体
 */
public class Authenticate implements Serializable
{
	private static final long serialVersionUID = 7425257751113997894L;
	
	
	public Context DbContext;
	
	/***
	 * 认证标识
	 */
	public int UserUniqueCode;

	/***
	 * 客户端唯一标识
	 */
	public int ClientUniqueCode;

	/***
	 * 配方体系
	 */
	public int FormulaSystemId;

	/***
	 * 数据访问程序集名称
	 */
	public String DataAccessAssembly;
	/// <summary>
    /// 数据库相对路径
    /// </summary>
    public String DatabasePath;

	/***
	 * 价格策略
	 */
    public PricingStrategy PricingStrategy;

	/***
	 * 客户的区域信息[范围必须从小到大排序]
	 */
	public String AreaCode;

}
